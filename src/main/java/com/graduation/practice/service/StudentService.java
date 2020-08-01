package com.graduation.practice.service;

import com.graduation.practice.entity.*;

import java.util.List;

public interface StudentService {
//    Student findUserByAccount(Student student);

    Student findStudentByStudentId(Student student);

    //    ==========================================================================
    //    学生界面
    User findUserByAccount(User user);
    // 成绩查询
    List<ScoreShow> findScoreByStudent(StudentToScore studentToScore);
    // 课表查询

}
