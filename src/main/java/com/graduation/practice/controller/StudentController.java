package com.graduation.practice.controller;

//学生的界面路由

import com.graduation.practice.entity.Result;
import com.graduation.practice.entity.ScoreShow;
import com.graduation.practice.entity.StudentToScore;
import com.graduation.practice.entity.User;
import com.graduation.practice.service.StudentService;
import com.graduation.practice.service.UserService;
import com.graduation.practice.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;

@RequestMapping("/student")
@Controller
public class StudentController {

    private final StudentService studentService;
    private final UserService userService;

    @Autowired
    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    // 登录
    @GetMapping("/login")
    public String loginHome() {
        return "studentPages/Login";
    }

    @GetMapping("/loginJudge")
    public String loginJudge(HttpServletRequest request) {
        String account = request.getParameter("name");
        String password = MD5Utils.code(request.getParameter("password"));
//        System.out.println(account);
//        System.out.println(password);
        User user = studentService.findUserByAccount(new User(account));
        // 结果对象
        Result<User> result = new Result<>();
        if (user == null) {
            result.setMessage("用户不存在");
        } else if (user.getPassword().equals(password)) {
            result.setMessage("登录成功");
            result.setData(user);
            // 存入session中
            request.getSession().setAttribute("user", user);
            return "redirect:home";
        } else {
            result.setMessage("密码错误");
        }
        return "redirect:login";
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
    @GetMapping("/profile")
    public String personShow(Model model) {
        return "studentPages/personInfo";
    }

    //    成绩查询
    @GetMapping("/score")
    public String scoreShow(Model model, HttpServletRequest request) {
        System.out.println(request.getSession());
        System.out.println(request.getParameter("value"));
//        int studentId = Integer.parseInt(request.getParameter("value"));
        int studentId = 18;
        Object scoreShow = studentService.findScoreByStudent(new StudentToScore(studentId));
        model.addAttribute("score_list", scoreShow);
        System.out.println(scoreShow);
//        scoreShow.getName();
        return "studentPages/scoreList";
    }

    // 注销
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        session.setAttribute("photoUrl", null);
        return "redirect:/login";
    }

    // 选课
    @GetMapping("/chooseClass")
    public String chooseClass(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return "studentPages/chooseClass";
    }

    //    用于测试界面的
    @GetMapping("/test")
    public String testShow(Model model) {
        return "studentPages/textTest";
    }

}
