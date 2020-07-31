package com.graduation.practice.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.Counselor;
import com.graduation.practice.entity.Result;
import com.graduation.practice.entity.Student;
import com.graduation.practice.entity.User;
import com.graduation.practice.service.CounselorService;
import com.graduation.practice.service.StudentService;
import com.graduation.practice.service.UserService;
import com.graduation.practice.utils.MD5Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.font.ScriptRun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping("/counselor")
@Controller
public class CounselorController {

    private final CounselorService counselorService;
    private final UserService userService;
    private final StudentService studentService;
    public CounselorController(CounselorService counselorService,UserService userService,StudentService studentService) {
        this.counselorService = counselorService;
        this.userService = userService;
        this.studentService = studentService;
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
    @GetMapping("/toUpdateStudent")
    public ModelAndView toUpdateStudent(HttpServletRequest request) {
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
    @PostMapping("/updateStudent")
    @ResponseBody
    public Result<User> updateStudent(HttpServletRequest request, HttpSession session) {
        // 参数
        String account = request.getParameter("studentId");
        String password = MD5Utils.code(request.getParameter("password"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        User user = new User(account, password);
        Result<User> result = new Result<>();
        if (counselorService.updateStudent(user) == 1) {
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

}
