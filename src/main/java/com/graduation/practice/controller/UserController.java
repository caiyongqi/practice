package com.graduation.practice.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.*;
import com.graduation.practice.service.CounselorService;
import com.graduation.practice.service.StudentService;
import com.graduation.practice.service.TeacherService;
import com.graduation.practice.service.UserService;
import com.graduation.practice.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RequestMapping("/user")
@Controller
public class UserController {
    @Value("${spring.mail.username}")
    private String from;

    private static HashMap<Integer, String> roles;

    static {
        roles = new HashMap<>();
        roles.put(1, "系统管理员");
        roles.put(2, "教务管理员");
        roles.put(3, "老师");
        roles.put(4, "辅导员");
        roles.put(5, "学生");
    }
    private JavaMailSender mailSender;
    private final UserService userService;
    private final TeacherService teacherService;
    private final CounselorService counselorService;
    private final StudentService studentService;

    @Autowired
    public UserController(JavaMailSender mailSender, UserService userService, TeacherService teacherService, CounselorService counselorService, StudentService studentService) {
        this.mailSender = mailSender;
        this.userService = userService;
        this.teacherService = teacherService;
        this.counselorService = counselorService;
        this.studentService = studentService;
    }

    public static void setRoles(HashMap<Integer, String> roles) {
        UserController.roles = roles;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request){
        if(request.getSession().getAttribute("user") != null){
            return "redirect:/user/home";
        }
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result<User> login(HttpServletRequest request) {
        // 获取参数
        String account = request.getParameter("account");
        String password = MD5Utils.code(request.getParameter("password"));
        /*
         *
         *
         * 待完成：
         * 如果是老师、学生、辅导员，需要继续查询得到photoUrl：session.setAttribute("photoUrl")
         *
         *
         *
         * */
        // 查询
        User user = userService.findUserByAccount(new User(account));
        // 结果对象
        Result<User> result = new Result<>();
        if (user == null) {
            result.setMessage("用户不存在");
        } else if (user.getPassword().equals(password)) {
            result.setMessage("登录成功");
            result.setData(user);
            // 存入session中
            request.getSession().setAttribute("user", user);
        } else {
            result.setMessage("密码错误");
        }
        return result;
    }

    @GetMapping("/findAllAdmin")
    public ModelAndView findAllAdmin(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, HttpSession session) {
        PageHelper.startPage(pageNum, pageSize);

        // 获取参数
        User user = (User) session.getAttribute("user");
        // 查询
        List<User> users = userService.findAllAdmin(user);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        // 视图
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin-list");
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    @GetMapping("/toAddUser")
    public String toAddUser() {
        return "add-user";
    }

    @PostMapping("/addUser")
    @ResponseBody
    public Result<User> addUser(HttpServletRequest request, HttpSession session) {
        // 参数
        String account = request.getParameter("account");
        String password = MD5Utils.code(request.getParameter("password"));
        int type = Integer.parseInt(request.getParameter("type"));
        String role = roles.get(type);
        User user = new User(account, password, type, role);
        User existedUser = userService.findUserByAccount(user);
        Result<User> result = new Result<>();
        if (existedUser != null) {
            result.setMessage("该用户已存在");
        } else if (userService.saveUser(user) == 1) {
            result.setMessage("添加用户成功");
        } else {
            result.setMessage("添加用户失败");
        }
        result.setData(user);
        return result;
    }

    // 批量删除
    @PostMapping("/deleteSelectedUser")
    public String deleteSelectedUser(HttpServletRequest request) {
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        String userList = request.getParameter("userList");
        String[] users = userList.split(",");
        List<String> accounts = new ArrayList<>();
        Collections.addAll(accounts, users);
        userService.deleteSelectedUser(accounts);
        return "redirect:findAllAdmin?pageNum=" + pageNum;
    }

    // 删除单个用户
    @GetMapping("/deleteUser/{pageNum}/{account}")
    public String deleteUser(@PathVariable("pageNum") int pageNum, @PathVariable("account") String account) {
        userService.deleteUser(account);
        return "redirect:/user/findAllAdmin?pageNum=" + pageNum;
    }

    // toUpdateUser
    // 如果使用路径参数就不能返回模板，静态资源无法加载
    @GetMapping("/toUpdateUser")
    public ModelAndView toUpdateUser(HttpServletRequest request) {
        String account = request.getParameter("account");
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        User user = userService.findUserByAccount(new User(account));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("update-user");
        mv.addObject("user", user);
        mv.addObject("pageNum", pageNum);
        return mv;
    }

    // 更新用户
    @PostMapping("/updateUser")
    @ResponseBody
    public Result<User> updateUser(HttpServletRequest request, HttpSession session) {
        // 参数
        String account = request.getParameter("account");
        String password = MD5Utils.code(request.getParameter("password"));
        int type = Integer.parseInt(request.getParameter("type"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        String role = roles.get(type);
        User user = new User(account, password, type, role);
        Result<User> result = new Result<>();
        if (userService.updateUser(user) == 1) {
            result.setMessage("更新用户成功");
        } else {
            result.setMessage("更新用户失败");
        }
        result.setData(user);
        return result;
    }

    // 搜索用户
    @RequestMapping("/searchUser")
    public ModelAndView searchUser(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String keyword, HttpSession session){
        session.setAttribute("keyword", keyword);
        PageHelper.startPage(pageNum, pageSize);
        // 获取参数
        User user = (User) session.getAttribute("user");
        // 查询
        List<User> users = userService.searchAllAdminByAccount(keyword, user.getAccount());
        PageInfo<User> pageInfo = new PageInfo<>(users);
        // 视图
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin-list");
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    // 注销
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        session.setAttribute("photoUrl", null);
        return "redirect:/user/";
    }

    @GetMapping("/student")
    public String getStudent(){
        return "teacher/list-student";
    }

    @GetMapping("/forgetPassword")
    public String resetPassword(){
        return "forget-password";
    }

    private static String getRandomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    @PostMapping("/code")
    @ResponseBody
    public Result<User> sendCode(HttpServletRequest request){
        Result<User> result = new Result<>();
        String account = request.getParameter("account");
        String email = request.getParameter("email");
        User user = userService.findUserByAccount(new User(account));
        if(user == null){
            result.setMessage("该用户不存在");
        }else if(user.getType()<3){
            result.setMessage("该用户未绑定邮箱");
        }else{
            boolean flag = false;
            switch (user.getType()){
                case 1:
                case 2:
                    result.setMessage("该用户未绑定邮箱");
                    break;
                case 3:
                    Teacher teacher = teacherService.findTeacherByTeacherId(new Teacher(account));
                    if (teacher.getEmail() == null){
                        result.setMessage("该用户未绑定邮箱");
                    }else if (!teacher.getEmail().equals(email)){
                        result.setMessage("输入邮箱错误");
                    }else{
                        flag = true;
                    }
                    break;
                case 4:
                    Counselor counselor = counselorService.findCounselorByCounselorId(new Counselor(account));
                    if (counselor.getEmail() == null){
                        result.setMessage("该用户未绑定邮箱");
                    }else if (!counselor.getEmail().equals(email)){
                        result.setMessage("输入邮箱错误");
                    }else{
                        flag = true;
                    }
                    break;
                case 5:
                    Student student = studentService.findStudentByStudentId(new Student(account));
                    if (student.getEmail() == null){
                        result.setMessage("该用户未绑定邮箱");
                    }else if (!student.getEmail().equals(email)){
                        result.setMessage("输入邮箱错误");
                    }else{
                        flag = true;
                    }
                    break;
            }
            if (flag){
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(from);
                mailMessage.setTo(email);
                mailMessage.setSubject("教务管理系统：验证码");
                String code = getRandomCode();
                mailMessage.setText("你的验证码为：" + code);
                request.getSession().setAttribute("code", code);
                mailSender.send(mailMessage);
                result.setMessage("验证码已发送");
            }
        }
        return result;
    }

    @PostMapping("/resetPassword")
    @ResponseBody
    public Result<User> resetPassword(HttpServletRequest request){
        Result<User> result = new Result<>();
        String account = request.getParameter("account");
        String password = MD5Utils.code(request.getParameter("password"));
        String code = request.getParameter("code");
        if(code == null || code.equals("")){
            if(userService.updateUser(new User(account, password)) == 1){
                result.setMessage("密码重置成功");
            }else{
                result.setMessage("密码重置成功");
            }
        }else{
            String realCode = (String) request.getSession().getAttribute("code");
            if (!code.equals(realCode)){
                result.setStatus(403);
                result.setMessage("验证码错误");
            }else if (userService.updateUser(new User(account, password)) == 1){
                result.setStatus(200);
                result.setMessage("密码重置成功");
            }else{
                result.setStatus(403);
                result.setMessage("密码重置失败");
            }
        }
        return result;
    }


    @GetMapping("/profile")
    public String toProfile(HttpServletRequest request, Model model){
        String account = request.getParameter("account");
        User user = userService.findUserByAccount(new User(account));
        String to = "";
        switch (user.getType()){
            case 1:
            case 2:
                model.addAttribute("admin", user);
                to = "/toResetPassword";
                break;
            case 3:
                Teacher teacher = teacherService.findTeacherByTeacherId(new Teacher(account));
                model.addAttribute("teacher", teacher);
                to = "/teacher/profile-teacher";
                break;
            case 4:
                Counselor counselor = counselorService.findCounselorByCounselorId(new Counselor(account));
                model.addAttribute("counselor", counselor);
                to = "/counselor/profile-counselor";
                break;
            case 5:
                Student student = studentService.findStudentByStudentId(new Student(account));
                model.addAttribute("student", student);
                to = "/student/profile-student";
                break;
        }
        return to;
    }
}
