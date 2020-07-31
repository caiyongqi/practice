package com.graduation.practice.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.Course;
import com.graduation.practice.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/course")
@Controller
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/toCourseDataList")
    public String toCourseDataList(@RequestParam(defaultValue = "1") int currentPage,String courseName, Model model){
        PageHelper.startPage(currentPage,5);
        List<Course> coureseList =  courseService.findAllCourse(courseName);
        PageInfo<Course> coursePageInfo = new PageInfo<Course>(coureseList,5);
        model.addAttribute("courseList",coureseList);
        model.addAttribute("pageInfo",coursePageInfo);
        return "courseDataList";
    }

    //跳转到新增课程页面
    @GetMapping("/toInsertCourse")
    public String toInsertCourse(Model model){
        return "insertCourse";
    }

    //
    @GetMapping("/insertCourse")
    public String insertCourse(Course course){
        try{
            courseService.insertCourse(course);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "false";
        }
        return "redirect:toCourseDataList";
    }
    //
    @GetMapping("/deleteCourse")
    public String deleteStudnet(String courseId){
        try{
            courseService.deleteCourse(courseId);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  "redirect:toCourseDataList";
    }
    //
    @GetMapping("/deleteSelected")
    public String deleteSelected(String courseList){
        System.out.println(courseList);
        String[] strs = courseList.split(",");
        List<String> ids = new ArrayList<>();
        for(String s : strs) {
            ids.add(s);
        }
        try{
            courseService.deleteSelectedCourse(ids);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:toCourseDataList";
    }

    //前往修改课程信息界面
    @GetMapping("/toUpdateCourse")
    public String toUpdateCourse(Model model,String courseId){
        Course course = courseService.findCourse(courseId);
        model.addAttribute("course",course);
        return "courseUpdate";
    }

    //
    @GetMapping("/updateCourse")
    public String updateCourse(Course course){
        System.out.println(course.toString());
        try{
            courseService.updateCourse(course);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "false";
        }
        return "redirect:toCourseDataList";
    }

}
