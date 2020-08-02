package com.graduation.practice.controller;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.Course;
import com.graduation.practice.entity.Teacher;
import com.graduation.practice.entity.TeacherToCourse;
import com.graduation.practice.service.CourseService;
import com.graduation.practice.service.TeacherService;
import com.graduation.practice.service.TeacherToCourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/teacherToCourse")
@Controller
public class TeacherToCourseController {
    private final TeacherToCourseService TTCService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    public TeacherToCourseController(TeacherToCourseService ttcService, TeacherService teacherServicel, CourseService courseService) {
        TTCService = ttcService;
        this.teacherService = teacherServicel;
        this.courseService = courseService;
    }


    @GetMapping("/toTeacherToCourseDataList")
    public String toTTCDataList(@RequestParam(defaultValue = "1") int currentPage, String teacherName, @RequestParam(defaultValue = "0") int courseId, Model model){
        //根据教师名称得到教师Id
        String teacherId = "";
        if(teacherService.findTeacherByName(teacherName) != null){
            teacherId = teacherService.findTeacherByName(teacherName).getTeacherId();
        }
        if(teacherName == null || teacherName.equals("")){
            teacherId = "";
        }
        PageHelper.startPage(currentPage,5);
        List<TeacherToCourse> TTCList = TTCService.getAllTTC(teacherId, courseId);
        PageInfo<TeacherToCourse> TTCPageInfo = new PageInfo<TeacherToCourse>(TTCList,5);
        List<Teacher> teacherList = teacherService.findAllTeacher();
        List<Course> courseList = courseService.findAllCourse("");


        //形成TTC.courseId->Course.name映射
        Map<Integer,String> TTCToCourseMap = new HashMap<Integer,String>();
        int i,j;
        for(i = 0;i <TTCList.size();i++){
            for(j = 0;j < courseList.size();j++){
                if(TTCList.get(i).getCourseId() == courseList.get(j).getId()){
                    TTCToCourseMap.put(TTCList.get(i).getCourseId(),courseList.get(j).getName());
                    break;
                }
            }
        }
        //形成TTC.teacherId->TeacherName映射
        Map<String,String> TTCToTeacherMap = new HashMap<String,String>();
        for(i = 0;i < TTCList.size();i++){
            for(j = 0;j < teacherList.size();j++){
                if(TTCList.get(i).getTeacherId().equals(teacherList.get(j).getTeacherId())){
                    TTCToTeacherMap.put(TTCList.get(i).getTeacherId(),teacherList.get(j).getName());
                    break;
                }
            }
        }
        model.addAttribute("pageInfo",TTCPageInfo);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("TTCList",TTCList);
        model.addAttribute("TTCToCourseMap",TTCToCourseMap);
        model.addAttribute("TTCToTeacherMap",TTCToTeacherMap);
        model.addAttribute("teacherName",teacherName);
        model.addAttribute("courseId",courseId);
        return "course/teacherToCourseDataList";
    }

    @GetMapping("/toInsertTeacherToCourse")
    public String toInsertTTC(Model model){
        List<Teacher> teacherList = teacherService.findAllTeacher();
        List<Course> courseList = courseService.findAllCourse("");
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("courseList",courseList);
        return "course/insertTeacherToCourse";
    }

    @GetMapping("/insertTeacherToCourse")
    public String insertTeacherToCourse(TeacherToCourse teacherToCourse){
        //TeacherToCourse teacherToCourse = new TeacherToCourse(teacherId, courseId,startTime,endTime,time);
        try{
            TTCService.insert(teacherToCourse);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:toTeacherToCourseDataList";
    }

    @GetMapping("/deleteTeacherToCourse")
    public String deleteTTC(String teacherId, int courseId, Date startTime, Date endTime, String time){
        TeacherToCourse ttc = new TeacherToCourse(teacherId,courseId,startTime,endTime,time);
        System.out.println(ttc.toString());
        try{
            TTCService.deleteTTC(ttc);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  "redirect:toTeacherToCourseDataList";
    }

    @PostMapping("/deleteSelectedCourse")
    public String ajaxList(HttpServletRequest request) throws Exception{

        //从流中读取数据
        BufferedReader br = request.getReader();
        String str = "";
        StringBuffer sb = new StringBuffer();
        while((str = br.readLine()) != null){
            sb.append(str);
        }
        System.out.println(sb);
        ObjectMapper mapper = new ObjectMapper();
        //使用jackson解析数据
        JavaType jt = mapper.getTypeFactory().constructParametricType(ArrayList.class, TeacherToCourse.class);
        List<TeacherToCourse> list = mapper.readValue(sb.toString(), jt);
        System.out.println(list);
        //无法反序列化，暂时未解决

        return "";
    }

    ////////////////////////


}
