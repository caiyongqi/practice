package com.graduation.practice.controller;

//学生的界面路由

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class UserHandler {


    @GetMapping("/index1")
    public String index(Model model) {
//        学生界面首页
        return "StudentHome";
    }

    @GetMapping("/index2")
    public String dataShow(Model model) {
        return "studentDataShow";
    }

    @GetMapping("/person")
    public String personShow(Model model) {
        return "personInfo";
    }

}
