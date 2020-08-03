package com.graduation.practice.controller;

import com.graduation.practice.entity.College;
import com.graduation.practice.entity.Result;
import com.graduation.practice.entity.User;
import com.graduation.practice.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/college")
@Controller
public class CollegeController {

    private final CollegeService collegeService;
    @Autowired
    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @GetMapping("/listColleges")
    public String findAllCollege(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/user/";
        }else if (user.getType() > 2){
            return "/error/404";
        }
        List<College> colleges = collegeService.findAllCollege();
        model.addAttribute("colleges", colleges);
        return "/college/list-college";
    }

    // 搜索学院
    @RequestMapping("/searchCollege")
    public ModelAndView searchUser(@RequestParam(defaultValue = "") String collegeKeyword, HttpSession session){
        session.setAttribute("collegeKeyword", collegeKeyword);
        // 获取参数
        List<College> colleges = collegeService.searchAllCollegeWithName(collegeKeyword);
        System.out.println(colleges);
        // 视图
        ModelAndView mv = new ModelAndView();
        mv.addObject("colleges", colleges);
        mv.setViewName("/college/list-college");
        return mv;
    }

    @GetMapping("/toAddCollege")
    public String toAddCollege(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/user/";
        }else if (user.getType() > 2){
            return "/error/404";
        }
        return "/college/add-college";
    }

    @PostMapping("/addCollege")
    @ResponseBody
    public Result<College> addCollege(HttpServletRequest request, HttpSession session) {
        // 参数
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");
        String address = request.getParameter("address");

        College college = new College(name, desc, address);
        College existedCollege = collegeService.findCollegeByName(college);
        Result<College> result = new Result<>();
        if (existedCollege != null) {
            result.setMessage("该学院已存在");
            result.setStatus(403);
        } else if (collegeService.saveCollege(college) == 1) {
            result.setMessage("添加学院成功");
            result.setStatus(200);
        } else {
            result.setMessage("添加学院失败");
            result.setStatus(403);
        }
        result.setData(college);
        return result;
    }

    // 删除单个学院
    @GetMapping("/deleteCollege")
    public String deleteUser(HttpServletRequest request) {
        collegeService.deleteCollegeById(new College(Integer.parseInt(request.getParameter("id"))));
        return "redirect:/college/listColleges";
    }

    @GetMapping("/toUpdateCollege")
    public String toUpdateCollege(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        College college = collegeService.findCollegeById(new College(id));
        model.addAttribute("college", college);
        return "/college/update-college";
    }

    @PostMapping("/updateCollege")
    @ResponseBody
    public Result<College> updateCollege(HttpServletRequest request, HttpSession session) {
        // 参数
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");
        String address = request.getParameter("address");

        College college = new College(id ,name, desc, address);
        College existedCollege = collegeService.findCollegeByName(college);
        Result<College> result = new Result<>();
        if (existedCollege != null && existedCollege.getId() != id) {
            result.setMessage("该学院已存在");
            result.setStatus(403);
        } else if (collegeService.updateCollege(college) == 1) {
            result.setMessage("更新学院信息成功");
            result.setStatus(200);
        } else {
            result.setMessage("更新学院信息失败");
            result.setStatus(403);
        }
        result.setData(college);
        return result;
    }
}
