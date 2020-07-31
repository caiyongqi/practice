package com.graduation.practice.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.Counselor;
import com.graduation.practice.entity.Student;
import com.graduation.practice.entity.User;
import com.graduation.practice.service.CounselorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/counselor")
@Controller
public class CounselorController {

    private final CounselorService counselorService;
    public CounselorController(CounselorService counselorService) {
        this.counselorService = counselorService;
    }

    //辅导员所带学生信息展示
    @GetMapping("/findAllStudent04")
    public ModelAndView findAllStudent04(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue= "5") int pageSize, String studentId, HttpSession session){
        // 获取参数
        User user = (User) session.getAttribute("user");
        PageHelper.startPage(pageNum, pageSize);
        // 查询
        List<Student> students = counselorService.findAllStudent(user);
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        System.out.println(pageInfo);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("counselor/studentDataList");
        mv.addObject("pageInfo", pageInfo);
        return mv;

    }

    // 搜索学生
    @RequestMapping("/searchStudent")
    public ModelAndView searchUser(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String keyword, HttpSession session){
        session.setAttribute("keyword", keyword);
        PageHelper.startPage(pageNum, pageSize);
        // 获取参数
        User user = (User) session.getAttribute("user");
        // 查询
        List<Student> students = counselorService.searchAllStudentById(keyword, user.getAccount());
        PageInfo<Student> pageInfo = new PageInfo<>(students);

        // 视图
        ModelAndView mv = new ModelAndView();
        mv.setViewName("counselor/studentDataList");
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }



}
