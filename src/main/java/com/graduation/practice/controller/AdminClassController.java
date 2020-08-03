package com.graduation.practice.controller;

//控制班级信息

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.*;
import com.graduation.practice.service.ClassService;
import com.graduation.practice.service.CollegeService;
import com.graduation.practice.service.DisciplineService;
import com.graduation.practice.service.UserService;
import com.graduation.practice.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequestMapping("/adminClass")
@Controller
public class AdminClassController {
    private final ClassService classService;
    private final UserService userService;
    private final DisciplineService disciplineService;
    private final CollegeService collegeService;

    @Autowired
    public AdminClassController(ClassService classService, UserService userService, DisciplineService disciplineService, CollegeService collegeService) {
        this.classService = classService;
        this.userService = userService;
        this.disciplineService = disciplineService;
        this.collegeService = collegeService;
    }

    @GetMapping("/findAllClasses")
    public String findAllClasses(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, HttpSession session, Model model) {

        PageHelper.startPage(pageNum, pageSize);
        // 查询
        List<ClassInfo> classes = classService.findAllClassInfo();
        PageInfo<ClassInfo> pageInfo = new PageInfo<>(classes);
        System.out.println(pageInfo);
        // 视图
//
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("/classesPages/classDatalist");
//        mv.addObject("pageInfo", pageInfo);
        model.addAttribute("pageInfo", pageInfo);
        return "classesPages/classesDatalist";
    }

    // 获取更新
    @GetMapping("/updateClass")
    public String updateClass(HttpSession session, Model model, HttpServletRequest request) {
        List<Discipline> disciplines = disciplineService.findAllDiscipline();
//        List<College> colleges = collegeService.findAllCollege();
        List<Classes> classes = classService.findAllClass();
        int id = Integer.parseInt(request.getParameter("id"));
        String className = request.getParameter("className");
        String disciplineName = request.getParameter("disciplineName");
        System.out.println(id);
        System.out.println(className);
        System.out.println(disciplineName);
//        model.addAttribute("pageInfo", pageInfo);
        int disciplineId = 0;
//        if (disciplines != null) {
//            for (Discipline discipline : disciplines
//            ) {
//                if (disciplineName.equals(discipline.getName())) {
//                    disciplines.remove(discipline);
//                }
//            }
//        }
        System.out.println(disciplineId);
        model.addAttribute("disciplines", disciplines);
        model.addAttribute("classes", classes);
        model.addAttribute("id", id);
        model.addAttribute("className", className);
        model.addAttribute("disciplineName", disciplineName);
        model.addAttribute("disciplineId", disciplineId);
        return "classesPages/updateClass";
    }

    // 更新逻辑
    @GetMapping("/updateLogic")
    @ResponseBody
    public Result<Classes> updateClassLogic(HttpServletRequest request, Model model) {
//        classId, className, disciplineId, collegeId;
        String className = request.getParameter("className");
        int classId = Integer.parseInt(request.getParameter("classId"));
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
//        System.out.println(className);
//        System.out.println(classId);
//        System.out.println(disciplineId);
        Result<Classes> result = new Result<>();
        int rows = classService.updateClassInfo(new Classes(classId, className, disciplineId));
        if (rows > 0) {
            result.setMessage("修改成功");
        }
        return result;
    }

    // 删除
    @PostMapping("/deleteClasses")
    public String deleteClasses(HttpServletRequest request) {

//        PageHelper.startPage(pageNum, pageSize);
//        // 查询
//        List<ClassInfo> classes = classService.findAllClassInfo();
//        PageInfo<ClassInfo> pageInfo = new PageInfo<>(classes);
//        System.out.println(pageInfo);
//        // 视图
////
////        ModelAndView mv = new ModelAndView();
////        mv.setViewName("/classesPages/classDatalist");
////        mv.addObject("pageInfo", pageInfo);
//        model.addAttribute("pageInfo", pageInfo);
        String classList = request.getParameter("studentList");
        String[] classes = classList.split(",");
        List<Integer> classIds = new ArrayList<>();
        for (String s : classes) {
            classIds.add(Integer.valueOf(s));
        }
        System.out.println(classIds);
        int rows = classService.deleteClass(classIds);
        System.out.println(rows);
//        studentService.deleteSelectedStudent(studentIds);
//        userService.deleteSelectedUser(studentIds);
        return "redirect:findAllClasses";
    }

    // 获取添加信息
    @GetMapping("/getCreateClasses")
    public String createClasses(Model model) {
        List<Discipline> disciplines = disciplineService.findAllDiscipline();
        List<College> colleges = collegeService.findAllCollege();
        System.out.println(colleges);
        System.out.println(disciplines);
        model.addAttribute("colleges", colleges);
        model.addAttribute("disciplines", disciplines);
        return "classesPages/addClass";
    }


    // 新建的逻辑
    @PostMapping("/createClasses")
    @ResponseBody
    public Result<Classes> createClasses(HttpServletRequest request, HttpSession session) {
        String className = request.getParameter("className");
        int classId = Integer.parseInt(request.getParameter("classId"));
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
//        int collegeId = request.getParameter("collegeId");
        int rows = classService.addClassInfo(new Classes(classId, className, disciplineId));
        System.out.println(rows);
        Result<Classes> result = new Result<>();
//        System.out.println(teacherId);
//        System.out.println();
//        if(existedTeacher != null){
//            result.setMessage("该用户已存在");
//        }else if(teacherService.saveTeacher(teacher) == 1){
//            result.setMessage("添加教师成功");
//        }else{
//            result.setMessage("添加教师失败");
//        }
//        result.setData(teacher);
        if (rows > 0) {
            result.setMessage("添加成功");
        }
//        result.setData();
        return result;
    }
}
