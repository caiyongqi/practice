package com.graduation.practice.controller;

import com.graduation.practice.entity.Result;
import com.graduation.practice.entity.User;
import com.graduation.practice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(){
        return "/login.html";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(HttpServletRequest request){
        // 获取参数
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        // 查询
        User user = userService.findUserByAccount(new User(account));
        // 结果对象
        Result<User> result = new Result<>();
        if(user == null){
            result.setMessage("用户不存在");
        }else if(user.getPassword().equals(password)){
            result.setMessage("登录成功");
            result.setData(user);
        }else{
            result.setMessage("密码错误");
        }
        return result;
    }
}
