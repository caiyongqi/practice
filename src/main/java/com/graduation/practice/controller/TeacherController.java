package com.graduation.practice.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.*;
import com.graduation.practice.service.*;
import com.graduation.practice.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.*;

@RequestMapping("/teacher")
@Controller
public class TeacherController {
    private final TeacherService teacherService;
    private final UserService userService;
    private final CollegeService collegeService;
    private final TeacherToCourseService teacherToCourseService;
    private final StudentToScoreService studentToScoreService;
    private final StudentService studentService;
    private final ClassService classService;
    @Autowired
    public TeacherController(TeacherService teacherService, UserService userService, CollegeService collegeService, TeacherToCourseService teacherToCourseService, StudentToScoreService studentToScoreService, StudentService studentService, ClassService classService) {
        this.teacherService = teacherService;
        this.userService = userService;
        this.collegeService = collegeService;
        this.teacherToCourseService = teacherToCourseService;
        this.studentToScoreService = studentToScoreService;
        this.studentService = studentService;
        this.classService = classService;
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
     public String findAllTaughtCourse(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/user/";
        }else if(user.getType() != 3){
            return "/error/404";
        }else{
            Teacher teacher = teacherService.findTeacherByTeacherId(new Teacher(user.getAccount()));
            List<TeacherToCourse> teacherAndCourses = teacherToCourseService.findAllCourseByTeacher(teacher);
            for (TeacherToCourse tc: teacherAndCourses) {
                tc.setStudentNum(studentToScoreService.getStudentNumByCourse(tc));
            }
            model.addAttribute("teacherAndCourses", teacherAndCourses);
        }
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
    public ModelAndView findAllStudentInCourse(HttpServletRequest request){
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        Date startTime = Date.valueOf(request.getParameter("startTime"));
        Date endTime = Date.valueOf(request.getParameter("endTime"));
        TeacherToCourse teacherToCourse = new TeacherToCourse(courseId, startTime, endTime);
        List<StudentToScore> studentToScores = studentToScoreService.findAllStudentByCourse(teacherToCourse);
        for (StudentToScore s: studentToScores) {
            Student student = studentService.findStudentById(new Student(s.getStudentId()));
            Classes classes = classService.findClassById(student.getClassId());
            student.setClasses(classes);
            s.setStudent(student);
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/teacher/list-student");
        mv.addObject("students", studentToScores);
        return mv;
    }

    @GetMapping("/score")
    public ModelAndView toUploadScore(HttpServletRequest request){
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        Date startTime = Date.valueOf(request.getParameter("startTime"));
        Date endTime = Date.valueOf(request.getParameter("endTime"));
        TeacherToCourse teacherToCourse = new TeacherToCourse(courseId, startTime, endTime);
        List<StudentToScore> studentToScores = studentToScoreService.findAllStudentByCourse(teacherToCourse);
        for (StudentToScore s: studentToScores) {
            Student student = studentService.findStudentById(new Student(s.getStudentId()));
            Classes classes = classService.findClassById(student.getClassId());
            student.setClasses(classes);
            s.setStudent(student);
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/teacher/upload-score");
        mv.addObject("students", studentToScores);
        mv.addObject("startTime", request.getParameter("startTime"));
        mv.addObject("endTime", request.getParameter("endTime"));
        mv.addObject("courseId", request.getParameter("courseId"));
        return mv;
    }

    // 上传成绩
    @PostMapping("/uploadScore")
    @ResponseBody
    public Result uploadScore(HttpServletRequest request){
        // 而得到学生学号列表
        String[] ids = request.getParameter("studentIds").split(",");

        // 得到id列表
        List<Integer> studentIds = new ArrayList<>();
        for (String id: ids) {
            Student student = studentService.findStudentByStudentId(new Student(id));
            studentIds.add(student.getId());
        }

        // 得到分数列表
        String[] studentScores = request.getParameter("scores").split(",");
        List<Float> scores = new ArrayList<>();
        for (String score: studentScores) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            scores.add(Float.parseFloat(decimalFormat.format(Float.parseFloat(score))));
        }

        // 得到StudentToScore对象集合
        List<StudentToScore> studentToScores = new ArrayList<>();
        for (int i=0; i<scores.size();i++){
            studentToScores.add(new StudentToScore(studentIds.get(i), scores.get(i)));
        }
        // 参数
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        Date startTime = Date.valueOf(request.getParameter("startTime"));
        Date endTime = Date.valueOf(request.getParameter("endTime"));

        Result result = new Result();
        if (studentToScoreService.updateScore(studentToScores) != 0){
            if(teacherToCourseService.updateHaveScore(new TeacherToCourse(courseId, startTime, endTime)) == 1){
                result.setMessage("成绩上传成功");
                result.setStatus(200);
            }else{
                result.setMessage("成绩上传失败");
                result.setStatus(500);
            }
        }else{
            result.setMessage("成绩上传失败");
            result.setStatus(500);
        }

        return result;
    }
    /**
     * 卡片布局数据量较少界面会错乱
     * 注意要补充足够的数据
     * **/
    @GetMapping("/chooseCourse")
    public String chooseCourse(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/user/";
        }else if(user.getType() != 3){
            return "/error/404";
        }else{
            Teacher teacher = teacherService.findTeacherByTeacherId(new Teacher(user.getAccount()));
            List<TeacherToCourse> teacherAndCourses = teacherToCourseService.findAllCourseByTeacher(teacher);
            for (TeacherToCourse tc: teacherAndCourses) {
                tc.setStudentNum(studentToScoreService.getStudentNumByCourse(tc));
            }
            model.addAttribute("teacherAndCourses", teacherAndCourses);
        }
        return "/teacher/choose-course";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/user/";
        }else if(user.getType() != 3){
            return "/error/404";
        }else{
            int courseNum = teacherToCourseService.getCourseNum(new TeacherToCourse(user.getAccount()));
//            int courseTaughtNum = teacherToCourseService.getCourseNum(new TeacherToCourse(user.getAccount(), 0));

            model.addAttribute("courseNum", courseNum);

            Teacher teacher = teacherService.findTeacherByTeacherId(new Teacher(user.getAccount()));
            List<TeacherToCourse> teacherAndCourses = teacherToCourseService.findAllCourseByTeacher(teacher);
            int courseTaughtNum = 0;
            for (TeacherToCourse tc: teacherAndCourses) {
                tc.setStudentNum(studentToScoreService.getStudentNumByCourse(tc));
                if (tc.getStudentNum() != 0 &&tc.getHaveScore() == 0) courseTaughtNum++;
            }
            model.addAttribute("courseTaughtNum", courseTaughtNum);
            model.addAttribute("teacherAndCourses", teacherAndCourses);

        }
        return "/teacher/teacher-home";
    }
}
