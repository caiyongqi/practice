package com.graduation.practice.controller;

import com.graduation.practice.entity.College;
import com.graduation.practice.entity.Discipline;
import com.graduation.practice.entity.Result;
import com.graduation.practice.entity.User;
import com.graduation.practice.service.CollegeService;
import com.graduation.practice.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/discipline")
@Controller
public class DisciplineController {
    private final DisciplineService disciplineService;
    private final CollegeService collegeService;
    @Autowired
    public DisciplineController(DisciplineService disciplineService, CollegeService collegeService) {
        this.disciplineService = disciplineService;
        this.collegeService = collegeService;
    }

    @GetMapping("/listDisciplines")
    public String findAllDiscipline(Model model, HttpSession session, HttpServletRequest request){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/user/";
        }else if (user.getType() > 2){
            return "/error/404";
        }
        List<Discipline> disciplines;
        if(request.getParameter("id") == null || request.getParameter("id").equals("0")){
            disciplines = disciplineService.findAllDisciplineAndCollege();
            model.addAttribute("collegeId", 0);
        }else{
            int id = Integer.parseInt(request.getParameter("id"));
            disciplines = disciplineService.findAllDisciplineByCollege(new College(id));
            model.addAttribute("collegeId", id);
        }
        List<College> colleges = collegeService.findAllCollege();
        model.addAttribute("disciplines", disciplines);
        model.addAttribute("colleges", colleges);
        return "/discipline/list-discipline";
    }

    @GetMapping("/toAddDiscipline")
    public String toAddCollege(HttpSession session, Model model, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "redirect:/user/";
        }else if (user.getType() > 2){
            return "/error/404";
        }
        List<College> colleges = collegeService.findAllCollege();
        model.addAttribute("colleges", colleges);
        if(request.getParameter("collegeId") == null || request.getParameter("collegeId").equals("0")){
            model.addAttribute("collegeId", 0);
        }else{
            int id = Integer.parseInt(request.getParameter("collegeId"));
            model.addAttribute("collegeId", id);
        }
        return "/discipline/add-discipline";
    }

    @PostMapping("/addDiscipline")
    @ResponseBody
    public Result<Discipline> addDiscipline(HttpServletRequest request, HttpSession session) {
        // 参数
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));

        Discipline discipline = new Discipline(name, desc, collegeId);
        Discipline existedDiscipline = disciplineService.findDisciplineByName(discipline);
        Result<Discipline> result = new Result<>();
        if (existedDiscipline != null) {
            result.setMessage("该专业已存在");
            result.setStatus(403);
        } else if (disciplineService.saveDiscipline(discipline) == 1) {
            result.setMessage("添加专业成功");
            result.setStatus(200);
        } else {
            result.setMessage("添加专业失败");
            result.setStatus(403);
        }
        result.setData(discipline);
        return result;
    }

    // 删除单个专业
    @GetMapping("/deleteDiscipline")
    public String deleteUser(HttpServletRequest request) {
        disciplineService.deleteDisciplineById(new Discipline(Integer.parseInt(request.getParameter("id"))));
        return "redirect:/discipline/listDisciplines?id=" + request.getParameter("collegeId");
    }

    @GetMapping("/toUpdateDiscipline")
    public String toUpdateCollege(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Discipline discipline = disciplineService.findDisciplineById(new Discipline(id));
        model.addAttribute("discipline", discipline);
        List<College> colleges = collegeService.findAllCollege();
        model.addAttribute("colleges", colleges);
        return "/discipline/update-discipline";
    }

    @PostMapping("/updateDiscipline")
    @ResponseBody
    public Result<Discipline> updateDiscipline(HttpServletRequest request, HttpSession session) {
        // 参数
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");
        int collegeId = Integer.parseInt(request.getParameter("collegeId"));

        Discipline discipline = new Discipline(id ,name, desc, collegeId);
        Discipline existedDiscipline = disciplineService.findDisciplineByName(discipline);
        Result<Discipline> result = new Result<>();
        if (existedDiscipline != null && existedDiscipline.getId() != id) {
            result.setMessage("该专业已存在");
            result.setStatus(403);
        } else if (disciplineService.updateDiscipline(discipline) == 1) {
            result.setMessage("更新专业信息成功");
            result.setStatus(200);
        } else {
            result.setMessage("更新专业信息失败");
            result.setStatus(403);
        }
        result.setData(discipline);
        return result;
    }
}
