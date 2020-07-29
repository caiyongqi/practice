package com.graduation.practice.controller;

import com.graduation.practice.entity.Classes;
import com.graduation.practice.entity.Student;
import com.graduation.practice.entity.User;
import com.graduation.practice.service.ClassService;
import com.graduation.practice.service.StudentService;
import com.graduation.practice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/student")
@Controller
public class StudentController {
    private final StudentService studentService;
    private final ClassService classService;
    private final UserService userServices;
    public StudentController(StudentService studentService, ClassService classService, UserService userServices) {
        this.studentService = studentService;
        this.classService = classService;
        this.userServices = userServices;
    }

    //展示所有学生信息
    @GetMapping("/toStudentDataList")
    public String toStudentDataList(Model model){
        List<Student> studentList =  studentService.findAllStudent();
        List<Classes> classList = classService.findAllClass();
        Map<Integer,String> map = new HashMap<Integer,String>();
        int i,j;
        //形成班级-学生映射表
        for(i = 0;i<studentList.size();i++){
            for(j = 0;j<classList.size();j++){
                if(studentList.get(i).getClassId() == classList.get(j).getId()){
                    map.put(studentList.get(i).getClassId(),classList.get(j).getName());
                    break;
                }
            }
        }
        System.out.println(studentList.toString());
        model.addAttribute("studentList",studentList);
        model.addAttribute("studentClassMap",map);
        return "studentDataList";
    }

    //跳转到新增学生页面
    @GetMapping("/toInsertStudent")
    public String toInsertStudent(Model model){
       System.out.println("toInsertStudent方法执行");
       List<Classes> classList = classService.findAllClass();
       System.out.println(classList.toString());
       model.addAttribute("classList",classList);
       return "insertStudent";
    }
    //新增学生
    @GetMapping("/insertStudent")
    public String insertStudent(Student student){
        System.out.println(student.toString());
        //先插入user再插入student（外键约束）
        User user = new User(student.getStudentId(),student.getStudentId(),"学生",5);
        try{
            userServices.insertUser(user);
            studentService.insertStudent(student);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "false";
        }
        return "redirect:toStudentDataList";
    }

}
