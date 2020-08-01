package com.graduation.practice.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.*;
import com.graduation.practice.service.ClassService;
import com.graduation.practice.service.CounselorService;
import com.graduation.practice.service.StudentService;
import com.graduation.practice.service.UserService;
import com.graduation.practice.utils.MD5Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequestMapping("/counselor")
@Controller
public class CounselorController {

    private final CounselorService counselorService;
    private final UserService userService;
    private final StudentService studentService;
    private final ClassService classService;
    public CounselorController(CounselorService counselorService,UserService userService,StudentService studentService,ClassService classService) {
        this.counselorService = counselorService;
        this.userService = userService;
        this.studentService = studentService;
        this.classService = classService;
    }

    //辅导员所带学生信息展示
    @GetMapping("/findAllStudent04")
    public ModelAndView findAllStudent04(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue= "10") int pageSize, HttpSession session){
        PageHelper.startPage(pageNum, pageSize);
        // 获取参数
        User user = (User) session.getAttribute("user");
        // 查询
        List<Student> students = counselorService.findAllStudent(user);
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("counselor/studentDataList");
        mv.addObject("pageInfo", pageInfo);
        return mv;

    }

    // 搜索学生
    @RequestMapping("/searchStudent")
    public ModelAndView searchUser(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String counselorStudentKeyword, HttpSession session){
        session.setAttribute("counselorStudentKeyword", counselorStudentKeyword);
        PageHelper.startPage(pageNum, pageSize);
        // 获取参数
        User user = (User) session.getAttribute("user");
        // 查询
        List<Student> students = counselorService.searchAllStudentById(counselorStudentKeyword, user.getAccount());
        PageInfo<Student> pageInfo = new PageInfo<>(students);

        // 视图
        ModelAndView mv = new ModelAndView();
        mv.setViewName("counselor/studentDataList");
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    // toUpdateStudent
    // 如果使用路径参数就不能返回模板，静态资源无法加载
    @GetMapping("/toUpdateStudentPassword")
    public ModelAndView toUpdateStudentPassword(HttpServletRequest request) {
        String studentId = request.getParameter("studentId");
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Student student= counselorService.findStudentByStudentId(new Student(studentId));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("counselor/update-studentPassword");
        mv.addObject("student", student);
        mv.addObject("pageNum", pageNum);
        return mv;
    }

    // 更新学生密码
    @PostMapping("/updateStudentPassword")
    @ResponseBody
    public Result<User> updateStudentPassword(HttpServletRequest request, HttpSession session) {
        // 参数
        String account = request.getParameter("studentId");
        String password = MD5Utils.code(request.getParameter("password"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        User user = new User(account, password);
        Result<User> result = new Result<>();
        if (counselorService.updateStudentPassword(user) == 1) {
            result.setMessage("更新用户成功");
        } else {
            result.setMessage("更新用户失败");
        }
        result.setData(user);
        return result;
    }

    @GetMapping("/counselorProfile")
    public String counselorProfile(HttpSession session){
        User user = (User) session.getAttribute("user");

        return "/counselor/counselorProfile";
    }
    
    // 批量删除
    @PostMapping("/deleteSelectedStudent")
    public String deleteSelectedstudent(HttpServletRequest request) {
        String studentList = request.getParameter("studentList");
        String[] students = studentList.split(",");
        List<String> studentIds = new ArrayList<>();
        Collections.addAll(studentIds, students);
        studentService.deleteSelectedStudent(studentIds);
        userService.deleteSelectedUser(studentIds);
        return "redirect:findAllStudent04";
    }

    @GetMapping("/toAddStudent")
    public String toAddStudent(Model model,HttpSession session){
        // 获取参数
        User user = (User) session.getAttribute("user");
        List<Classes> classes = counselorService.findClassByCounselorId(user);
        model.addAttribute("classes", classes);
        return "/counselor/add-student";
    }

    @PostMapping("/addStudent")
    @ResponseBody
    public Result<Student> addStudent(HttpServletRequest request, HttpSession session){
        // 参数
        String studentId = request.getParameter("studentId");
        String password = MD5Utils.code(request.getParameter("password"));
        String name = request.getParameter("name");
        int gender = Integer.parseInt(request.getParameter("gender"));
        int classId = Integer.parseInt(request.getParameter("classesId"));
        String dateTem = request.getParameter("date");
        java.sql.Date date = java.sql.Date.valueOf(dateTem);
        User user = new User(studentId, password, 5, "学生");
        userService.saveUser(user);
        Student student = new Student(studentId, name, gender, classId,date);
        Student existedStudent = studentService.findStudentByStudentId(student);
        Result<Student> result = new Result<>();
        if(existedStudent != null){
            result.setMessage("该用户已存在");
        }else if(counselorService.insertStudent(student) == 1){
            result.setMessage("添加学生成功");
        }else{
            result.setMessage("添加学生失败");
        }
        result.setData(student);
        return result;
    }

    // 删除单个用户
    @GetMapping("/deleteStudent/{pageNum}/{studentId}")
    public String deleteStudent(@PathVariable("pageNum") int pageNum, @PathVariable("studentId") String studentId){
       // studentService.deleteStudent(studentId);
        userService.deleteUser(studentId);
        return "redirect:/counselor/findAllStudent04?pageNum=" + pageNum;
    }

    @GetMapping("/studentProfile")
    public String toProfile(HttpServletRequest request, Model model){
        String studentId = request.getParameter("studentId");
        Student student = studentService.findStudentByStudentId(new Student(studentId));
        model.addAttribute("student", student);
        //model.addAttribute("courseNum", studentService.getCourseNumBystudentId(studentId));
        return "/counselor/profile-student";
    }

    // toUpdatestudent
    // 如果使用路径参数就不能返回模板，静态资源无法加载
    @GetMapping("/toUpdateStudent")
    public ModelAndView toUpdateStudent( HttpServletRequest request,HttpSession session){
        String studentId = request.getParameter("studentId");
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Student student = studentService.findStudentByStudentId(new Student(studentId));
        System.out.println(student);
        User user = (User) session.getAttribute("user");
        List<Classes> classes = counselorService.findClassByCounselorId(user);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/counselor/update-student");
        mv.addObject("student", student);
        mv.addObject("pageNum", pageNum);
        mv.addObject("classes", classes);
        return mv;
    }

    // 更新用户
    @PostMapping("/updateStudent")
    @ResponseBody
    public Result<Student> updateStudent(@RequestParam("photo") MultipartFile file, HttpServletRequest request){
        Result<Student> result = new Result<>();
        String photoUrl = null;
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();  // 文件名
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String filePath = "C:/Users/samsung/IdeaProjects/practice/src/main/resources/static/photo/"; // 上传后的路径
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            photoUrl = "/photo/" + fileName;
        }

        // 参数
        String name = request.getParameter("name");
        String studentId = request.getParameter("studentId");
        int age = Integer.parseInt(request.getParameter("age"));
        int gender = Integer.parseInt(request.getParameter("gender"));
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        int classId = Integer.parseInt(request.getParameter("classesId"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        String dateTem = request.getParameter("date");
        java.sql.Date date = java.sql.Date.valueOf(dateTem);
        Student student = new Student(studentId, name, age, gender, address, email, phoneNumber, classId, photoUrl, date);

        if(counselorService.updateStudent(student) == 1){
            result.setMessage("更新用户成功");
        }else{
            result.setMessage("更新用户失败");
        }
        result.setData(student);
        return result;
    }



}
