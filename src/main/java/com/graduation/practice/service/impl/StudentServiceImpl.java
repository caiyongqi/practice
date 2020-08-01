package com.graduation.practice.service.impl;

import com.graduation.practice.dao.StudentDao;
import com.graduation.practice.entity.*;
import com.graduation.practice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
//    private StudentDao studentDao;
//
//    @Override
//    public Student findUserByAccount(Student student) {
//        return studentDao.findStudentByAccount(student);

    private final StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student findStudentByStudentId(Student student) {
        return studentDao.findStudentByStudentId(student);
    }

    //==========================================================================
    //    学生
    @Override
    public User findUserByAccount(User user) {
        Student student = studentDao.findStudentInfo(user);
        System.out.println(student);
        User user1 = studentDao.findUserByAccount(user);
        System.out.println(user1);
        return studentDao.findUserByAccount(user);
    }


    //    成绩查询
    @Override
    public List<ScoreShow> findScoreByStudent(StudentToScore studentToScore) {
        System.out.println(studentToScore);
        // 获取学生分数信息
        List<ScoreShow> scoreShows = studentDao.findStudentScore(studentToScore);
        System.out.println(scoreShows);
//        System.out.println(course);
        return scoreShows;
    }
}
