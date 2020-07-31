package com.graduation.practice.controller;

//学生的界面路由

import com.graduation.practice.entity.Result;
import com.graduation.practice.entity.Student;
import com.graduation.practice.service.StudentService;
import com.graduation.practice.utils.MD5Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/student")
@Controller
public class StudentController {

    private StudentService studentService;

    // 登录
    @GetMapping("/login")
    public String loginHome() {
        return "studentPages/Login";
    }

    @GetMapping("/loginJudge")
    public String loginJudge(HttpServletRequest request) {
        int account = Integer.parseInt(request.getParameter("account"));
        String password = MD5Utils.code(request.getParameter("password"));
        // 查询
        Student user = studentService.findUserByAccount(new Student(account));
        // 结果对象
        Result<Student> result = new Result<>();
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
        return "redirect:student/home";
    }

    //        学生界面首页
    @GetMapping("/home")
    public String index(Model model) {
        return "studentPages/studentHome";
    }

    //    课表
    @GetMapping("/datalist")
    public String dataShow(Model model) {
        return "studentPages/studentDataShow";
    }

    //    个人页
    @GetMapping("/person")
    public String personShow(Model model) {
        return "studentPages/personInfo";
    }

    //    成绩查询
    @GetMapping("/score")
    public String scoreShow(Model model) {
        return "studentPages/scoreList";
    }

    //    用于测试界面的
    @GetMapping("/test")
    public String testShow(Model model) {
        return "studentPages/textTest";
    }

}
