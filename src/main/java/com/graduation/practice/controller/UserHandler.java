package com.graduation.practice.controller;


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
        return "/pages/home";
    }

}
