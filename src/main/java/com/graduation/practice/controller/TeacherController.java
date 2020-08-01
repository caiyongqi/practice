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
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
    /**
     *
     * 管理员操作部分
     *
     * **/
    @GetMapping("/findAllTeacher")
    public ModelAndView findAllTeacher(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, HttpSession session){

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
        List<College> colleges = collegeService.findAllCollege();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/teacher/update-teacher");
        mv.addObject("teacher", teacher);
        mv.addObject("pageNum", pageNum);
        mv.addObject("colleges", colleges);
        return mv;
    }

    // 更新用户
    @PostMapping("/updateTeacher")
    @ResponseBody
    public Result<Teacher> updateTeacher(HttpServletRequest request){
        Result<Teacher> result = new Result<>();
        String photoUrl = null;
        MultipartFile file = ((MultipartRequest) request).getFile("photo");
        if (file != null) {
            String fileName = file.getOriginalFilename();  // 文件名
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String filePath = "F:/IDEA_projects/practice/src/main/resources/static/photo/"; // 上传后的路径
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            photoUrl = "/photo/" + fileName;
        }

        // 参数
        String name = request.getParameter("name");
        String teacherId = request.getParameter("teacherId");
        int age = Integer.parseInt(request.getParameter("age"));
        int gender = Integer.parseInt(request.getParameter("gender"));
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));

        Teacher teacher = new Teacher(teacherId, name, age, gender, address, email, phoneNumber, collegeId, photoUrl);

        if (photoUrl == null){
            photoUrl = teacherService.findTeacherByTeacherId(teacher).getPhotoUrl();
        }

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

    @GetMapping("/profile")
    public String toProfile(HttpServletRequest request, Model model){
        String teacherId = request.getParameter("teacherId");
        Teacher teacher = teacherService.findTeacherByTeacherId(new Teacher(teacherId));
        model.addAttribute("teacher", teacher);
        model.addAttribute("courseNum", teacherService.getCourseNumByTeacherId(teacherId));
        return "/teacher/profile-teacher";
    }

    /**
     *
     * 老师操作部分
     *
     * **/
    // 所教课程
    @GetMapping("/course")
     public String findAllTaughtCourse(HttpServletRequest request, Model model){

        return "/teacher/course";
    }
    // 跳转到更新老师个人信息的界面
    @GetMapping("/toUpdateInfo")
    public String toUpdateInfo(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/user/";
        }else{
            if (user.getType() == 3){
                Teacher teacher = teacherService.findTeacherByTeacherId(new Teacher(user.getAccount()));
                model.addAttribute("teacher", teacher);
                return "/teacher/update-personal-info";
            }else{
                return "/error/404";
            }
        }
    }

    // 更新用户
    @PostMapping("/updateInfo")
    @ResponseBody
    public Result<Teacher> toUpdateInfo(HttpServletRequest request){
        Result<Teacher> result = new Result<>();
        String photoUrl = null;
        MultipartFile file = ((MultipartRequest) request).getFile("photo");
        if (file != null) {
            String fileName = file.getOriginalFilename();  // 文件名
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String filePath = "F:/IDEA_projects/practice/src/main/resources/static/photo/"; // 上传后的路径
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            photoUrl = "/photo/" + fileName;
        }

        // 参数
        String name = request.getParameter("name");
        String teacherId = request.getParameter("teacherId");
        int age = Integer.parseInt(request.getParameter("age"));
        int gender = Integer.parseInt(request.getParameter("gender"));
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String desc = request.getParameter("desc");
        String education = request.getParameter("education");

        Teacher teacher = new Teacher(teacherId, name, age, gender, address, email, phoneNumber, photoUrl, education, desc);
        if (photoUrl == null){
            photoUrl = teacherService.findTeacherByTeacherId(teacher).getPhotoUrl();
        }
        if(teacherService.updateTeacher(teacher) == 1){
            result.setMessage("更新用户成功");
        }else{
            result.setMessage("更新用户失败");
        }
        result.setData(teacher);
        return result;
    }

    // 课程下的学生
    @GetMapping("/student")
    public String findAllStudentInCourse(HttpServletRequest request, Model model){

        return "/teacher/list-student";
    }

    @GetMapping("/score")
    public String toUploadScore(){
        return "/teacher/upload-score";
    }

    // 上传成绩
    @PostMapping("/uploadScore")
    @ResponseBody
    public Result uploadScore(HttpServletRequest request){
        String[] studentIds = request.getParameter("studentIds").split(",");
        String[] studentScores = request.getParameter("scores").split(",");
        List<Float> scores = new ArrayList<>();
        for (String score: studentScores) {
            scores.add(Float.parseFloat(score));
        }

        System.out.println(Arrays.toString(studentIds));
        System.out.println(Arrays.toString(studentScores));
        Result result = new Result();
        result.setMessage("成绩上传成功");
        return result;
    }

    @GetMapping("/chooseCourse")
    public String chooseCourse(){
        return "/teacher/choose-course";
    }
}
