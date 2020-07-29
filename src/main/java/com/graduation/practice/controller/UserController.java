package com.graduation.practice.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.Result;
import com.graduation.practice.entity.User;
import com.graduation.practice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RequestMapping("/user")
@Controller
public class UserController {

    private static HashMap<Integer, String> roles;

    static {
        roles = new HashMap<>();
        roles.put(1, "系统管理员");
        roles.put(2, "教务管理员");
        roles.put(3, "老师");
        roles.put(4, "辅导员");
        roles.put(5, "学生");
    }

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public static void setRoles(HashMap<Integer, String> roles) {
        UserController.roles = roles;
    }

    @GetMapping("/")
    public String index() {
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
        String password = request.getParameter("password");

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
        String password = request.getParameter("password");
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
        String userList = request.getParameter("userList");
        String[] users = userList.split(",");
        List<String> accounts = new ArrayList<>();
        Collections.addAll(accounts, users);
        System.out.println(accounts);
        userService.deleteSelectedUser(accounts);
        return "redirect:findAllAdmin";
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
        String password = request.getParameter("password");
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

    @GetMapping("/index2")
    public String dataShow() {
        return "studentDataShow";
    }
}
