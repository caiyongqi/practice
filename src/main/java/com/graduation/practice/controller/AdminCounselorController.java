package com.graduation.practice.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.*;
import com.graduation.practice.service.CounselorService;
import com.graduation.practice.service.DisciplineService;
import com.graduation.practice.service.StudentService;
import com.graduation.practice.service.UserService;
import com.graduation.practice.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequestMapping("/adminCounselor")
@Controller
public class AdminCounselorController {
    private final CounselorService counselorService;
    private final DisciplineService disciplineService;
    private final UserService userService;
    @Autowired
    public AdminCounselorController(CounselorService counselorService,DisciplineService disciplineService,UserService userService){
        this.counselorService = counselorService;
        this.disciplineService = disciplineService;
        this.userService = userService;
    }
    @GetMapping("/findAllCounselor")
    public ModelAndView findAllCounselor(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, HttpSession session){

        PageHelper.startPage(pageNum, pageSize);
        // 查询
        List<Counselor> counselors = counselorService.findAllCounselor();
        PageInfo<Counselor> pageInfo = new PageInfo<>(counselors);
        // 视图
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/counselor/list-counselor");
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    @GetMapping("/toAddCounselor")
    public String toAddCounselor(Model model){
        List<Discipline> disciplines = disciplineService.findAllDiscipline();
        model.addAttribute("disciplines", disciplines);
        return "/counselor/add-counselor";
    }

    @PostMapping("/addCounselor")
    @ResponseBody
    public Result<Counselor> addCounselor(HttpServletRequest request, HttpSession session){
        // 参数
        String counselorId = request.getParameter("counselorId");
        String password = MD5Utils.code(request.getParameter("password"));
        String name = request.getParameter("name");
        int gender = Integer.parseInt(request.getParameter("gender"));
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
        User user = new User(counselorId, password, 4, "辅导员");
        userService.saveUser(user);
        Counselor counselor = new Counselor(counselorId, name, gender, disciplineId);
        Counselor existedCounselor = counselorService.findCounselorByCounselorId(counselor);
        Result<Counselor> result = new Result<>();
        if(existedCounselor != null){
            result.setMessage("该用户已存在");
        }else if(counselorService.saveCounselor(counselor) == 1){
            result.setMessage("添加辅导员成功");
        }else{
            result.setMessage("添加辅导员失败");
        }
        result.setData(counselor);
        return result;
    }

    // 批量删除
    @PostMapping("/deleteSelectedCounselor")
    public String deleteSelectedCounselor(HttpServletRequest request) {
        String counselorList = request.getParameter("counselorList");
        String[] counselors = counselorList.split(",");
        List<String> counselorIds = new ArrayList<>();
        Collections.addAll(counselorIds, counselors);
        counselorService.deleteSelectedCounselor(counselorIds);
        userService.deleteSelectedUser(counselorIds);
        return "redirect:findAllCounselor";
    }

    // 搜索用户
    @RequestMapping("/searchCounselor")
    public ModelAndView searchCounselor(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String adminCounselorKeyword, HttpSession session){
        session.setAttribute("adminCounselorKeyword", adminCounselorKeyword);
        PageHelper.startPage(pageNum, pageSize);

        List<Counselor> counselors = counselorService.searchAllCounselorByCounselorName(adminCounselorKeyword);
        PageInfo<Counselor> pageInfo = new PageInfo<>(counselors);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/counselor/list-counselor");
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    // toUpdatecounselor
    // 如果使用路径参数就不能返回模板，静态资源无法加载
    @GetMapping("/toUpdateCounselor")
    public ModelAndView toUpdateCounselor( HttpServletRequest request){
        String counselorId = request.getParameter("counselorId");
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Counselor counselor = counselorService.findCounselorByCounselorId(new Counselor(counselorId));
        System.out.println(counselor);
        List<Discipline> disciplines = disciplineService.findAllDiscipline();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/counselor/update-counselor");
        mv.addObject("counselor", counselor);
        mv.addObject("pageNum", pageNum);
        mv.addObject("disciplines", disciplines);
        return mv;
    }

    // 更新用户
    @PostMapping("/updateCounselor")
    @ResponseBody
    public Result<Counselor> updateCounselor(@RequestParam("photo") MultipartFile file, HttpServletRequest request){
        Result<Counselor> result = new Result<>();
        String photoUrl = null;
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();  // 文件名
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            String filePath = "C:/Users/samsung/ideaProjects/practice/src/main/resources/static/photo/"; // 上传后的路径
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
        String counselorId = request.getParameter("counselorId");
        int age = Integer.parseInt(request.getParameter("age"));
        int gender = Integer.parseInt(request.getParameter("gender"));
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        int disciplineId = Integer.parseInt(request.getParameter("disciplineId"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));

        Counselor counselor = new Counselor(counselorId, name, age, gender, address, email, phoneNumber, disciplineId, photoUrl);

        if(counselorService.updateCounselor(counselor) == 1){
            result.setMessage("更新用户成功");
        }else{
            result.setMessage("更新用户失败");
        }
        result.setData(counselor);
        return result;
    }

    @GetMapping("/profile")
    public String toProfile(HttpServletRequest request, Model model){
        String counselorId = request.getParameter("counselorId");
        Counselor counselor = counselorService.findCounselorProfileByCounselorId(new Counselor(counselorId));
        model.addAttribute("counselor", counselor);
       // model.addAttribute("courseNum", counselorService.getCourseNumBycounselorId(counselorId));
        return "/counselor/profile-counselor";
    }

    // 删除单个用户
    @GetMapping("/deleteCounselor/{pageNum}/{counselorId}")
    public String deleteCounselor(@PathVariable("pageNum") int pageNum, @PathVariable("counselorId") String counselorId){
        counselorService.deleteCounselor(counselorId);
        userService.deleteUser(counselorId);
        return "redirect:/adminCounselor/findAllCounselor?pageNum=" + pageNum;
    }

}
