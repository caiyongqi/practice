package com.graduation.practice.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.College;
import com.graduation.practice.entity.Result;
import com.graduation.practice.entity.Teacher;
import com.graduation.practice.entity.User;
import com.graduation.practice.service.CollegeService;
import com.graduation.practice.service.TeacherService;
import com.graduation.practice.service.UserService;
import com.graduation.practice.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping("/teacher")
@Controller
public class TeacherController {
    private final TeacherService teacherService;
    private final UserService userService;
    private final CollegeService collegeService;
    @Autowired
    public TeacherController(TeacherService teacherService, UserService userService, CollegeService collegeService) {
        this.teacherService = teacherService;
        this.userService = userService;
        this.collegeService = collegeService;
    }

    @GetMapping("/findAllTeacher")
    public ModelAndView findAllAdmin(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, HttpSession session){

        PageHelper.startPage(pageNum, pageSize);
        // 查询
        List<Teacher> teachers = teacherService.findAllTeacher();
        PageInfo<Teacher> pageInfo = new PageInfo<>(teachers);
        // 视图
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/teacher/list-teacher");
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    @GetMapping("/toAddTeacher")
    public String toAddTeacher(Model model){
        List<College> colleges = collegeService.findAllCollege();
        model.addAttribute("colleges", colleges);
        return "/teacher/add-teacher";
    }

    @PostMapping("/addTeacher")
    @ResponseBody
    public Result<Teacher> addTeacher(HttpServletRequest request, HttpSession session){
        // 参数
        String teacherId = request.getParameter("teacherId");
        String password = MD5Utils.code(request.getParameter("password"));
        String name = request.getParameter("name");
        int gender = Integer.parseInt(request.getParameter("gender"));
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        User user = new User(teacherId, password, 3, "教师");
        userService.saveUser(user);
        Teacher teacher = new Teacher(teacherId, name, gender, collegeId);
        Teacher existedTeacher = teacherService.findTeacherByTeacherId(teacher);
        Result<Teacher> result = new Result<>();
        if(existedTeacher != null){
            result.setMessage("该用户已存在");
        }else if(teacherService.saveTeacher(teacher) == 1){
            result.setMessage("添加教师成功");
        }else{
            result.setMessage("添加教师失败");
        }
        result.setData(teacher);
        return result;
    }

    // 批量删除
    @PostMapping("/deleteSelectedTeacher")
    public String deleteSelectedTeacher(HttpServletRequest request) {
        String teacherList = request.getParameter("teacherList");
        String[] teachers = teacherList.split(",");
        List<String> teacherIds = new ArrayList<>();
        Collections.addAll(teacherIds, teachers);
        teacherService.deleteSelectedTeacher(teacherIds);
        userService.deleteSelectedUser(teacherIds);
        return "redirect:findAllTeacher";
    }

    // 删除单个用户
    @GetMapping("/deleteTeacher/{pageNum}/{teacherId}")
    public String deleteTeacher(@PathVariable("pageNum") int pageNum, @PathVariable("teacherId") String teacherId){
        teacherService.deleteTeacher(teacherId);
        userService.deleteUser(teacherId);
        return "redirect:/teacher/findAllTeacher?pageNum=" + pageNum;
    }

    // toUpdateTeacher
    // 如果使用路径参数就不能返回模板，静态资源无法加载
    @GetMapping("/toUpdateTeacher")
    public ModelAndView toUpdateTeacher( HttpServletRequest request){
        String teacherId = request.getParameter("teacherId");
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Teacher teacher = teacherService.findTeacherByTeacherId(new Teacher(teacherId));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("update-teacher");
        mv.addObject("teacher", teacher);
        mv.addObject("pageNum", pageNum);
        return mv;
    }

    // 更新用户
    @PostMapping("/updateTeacher")
    @ResponseBody
    public Result<Teacher> updateTeacher(HttpServletRequest request, HttpSession session){
        // 参数
        String teacherId = request.getParameter("teacherId");
//        String password = MD5Utils.code(request.getParameter("password"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
//        Teacher teacher = new Teacher(teacherId, password);
        Teacher teacher = new Teacher(teacherId);
        Result<Teacher> result = new Result<>();
        if(teacherService.updateTeacher(teacher) == 1){
            result.setMessage("更新用户成功");
        }else{
            result.setMessage("更新用户失败");
        }
        result.setData(teacher);
        return result;
    }

    // 搜索用户
    @RequestMapping("/searchTeacher")
    public ModelAndView searchTeacher(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String teacherKeyword, HttpSession session){
        session.setAttribute("teacherKeyword", teacherKeyword);
        PageHelper.startPage(pageNum, pageSize);

        List<Teacher> teachers = teacherService.searchAllTeacherByTeacherName(teacherKeyword);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teachers);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/teacher/list-teacher");
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }
}
