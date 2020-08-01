package com.graduation.practice.service;

import com.graduation.practice.entity.*;
import com.graduation.practice.entity.Student;
import com.graduation.practice.entity.Teacher;

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

    List<Student> findAllStudent(String studentName, int classId);
    int insertStudent(Student student);
    int updateStudent(Student student);
    Student adminFindStudentByStudentId(String studentID);

    int deleteSelectedStudent(List<String> studentIdList);

    Student findStudentById(Student student);

}
