package com.graduation.practice.service.impl;

import com.graduation.practice.dao.StudentToScoreDao;
import com.graduation.practice.entity.StudentToScore;
import com.graduation.practice.entity.TeacherToCourse;
import com.graduation.practice.service.StudentToScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentToScoreServiceImpl implements StudentToScoreService {

    private StudentToScoreDao studentToScoreDao;
    @Autowired
    public StudentToScoreServiceImpl(StudentToScoreDao studentToScoreDao) {
        this.studentToScoreDao = studentToScoreDao;
    }

    @Override
    public int getStudentNumByCourse(TeacherToCourse teacherToCourse) {
        return studentToScoreDao.getStudentNumByCourse(teacherToCourse);
    }

    @Override
    public List<StudentToScore> findAllStudentByCourse(TeacherToCourse teacherToCourse) {
        return studentToScoreDao.findAllStudentByCourse(teacherToCourse);
    }
}
