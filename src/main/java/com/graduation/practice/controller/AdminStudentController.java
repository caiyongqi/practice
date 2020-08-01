package com.graduation.practice.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/adminStudent")
@Controller
public class AdminStudentController {
    private final StudentService studentService;
    private final ClassService classService;
    private final UserService userService;
    public AdminStudentController(StudentService studentService, ClassService classService, UserService userServices) {
        this.studentService = studentService;
        this.classService = classService;
        this.userService = userServices;
    }

    //展示所有学生信息
    @GetMapping("/toStudentDataList")
    public String toStudentDataList(@RequestParam(defaultValue = "1") int currentPage, String studentName,@RequestParam(defaultValue = "0") int classId, Model model){
        //pagehelper
        System.out.println("---------------当前页数:"+currentPage);
        //System.out.println(studentName+classId);
        PageHelper.startPage(currentPage,5);
        List<Student> studentList =  studentService.findAllStudent(studentName,classId);
        PageInfo<Student> studentPageInfo = new PageInfo<Student>(studentList,5);
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
        model.addAttribute("classList",classList);
        model.addAttribute("studentList",studentList);
        model.addAttribute("studentClassMap",map);
        model.addAttribute("pageInfo",studentPageInfo);
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
            userService.insertUser(user);
            studentService.insertStudent(student);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "false";
        }
        return "redirect:toStudentDataList";
    }
    //删除学生，仅需删除User，对应CASCADE会自动更新学生表
    @GetMapping("/deleteStudent")
    public String deleteStudnet(String studentId){
        try{
            userService.deleteUser(studentId);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  "redirect:toStudentDataList";
    }
    //删除选中学生,也仅需删除User
    @GetMapping("/deleteSelected")
    public String deleteSelected(String studentList){
        System.out.println(studentList);
        String[] strs = studentList.split(",");
        List<String> accounts = new ArrayList<>();
        for(String s : strs) {
            accounts.add(s);
        }
        try{
            userService.deleteSelectedUser(accounts);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:toStudentDataList";
    }

    //前往修改学生信息界面
    @GetMapping("/toUpdateStudent")
    public String toUpdateStudent(Model model,String studentId){
        System.out.println(studentId);
        Student student= studentService.adminFindStudentByStudentId(studentId);
        System.out.println(student.toString());
        List<Classes> classList = classService.findAllClass();
        model.addAttribute("classList",classList);
        model.addAttribute("student",student);
        return "studentUpdate";
    }
    //更新学生
    @GetMapping("/updateStudent")
    public String updateStudent(Student student){
        System.out.println(student.toString());
        try{
            studentService.updateStudent(student);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "false";
        }
        return "redirect:toStudentDataList";
    }
}
