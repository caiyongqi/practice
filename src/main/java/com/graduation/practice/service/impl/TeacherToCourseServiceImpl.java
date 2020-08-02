package com.graduation.practice.service.impl;

import com.graduation.practice.dao.TeacherToCourseDao;
import com.graduation.practice.entity.Teacher;
import com.graduation.practice.entity.TeacherToCourse;
import com.graduation.practice.service.TeacherToCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherToCourseServiceImpl implements TeacherToCourseService {

    private TeacherToCourseDao teacherToCourseDao;
    @Autowired
    public TeacherToCourseServiceImpl(TeacherToCourseDao teacherToCourseDao) {
        this.teacherToCourseDao = teacherToCourseDao;
    }

    @Override
    public List<TeacherToCourse> findAllCourseByTeacher(Teacher teacher) {
        return teacherToCourseDao.findAllCourseByTeacher(teacher);
    }

    @Override
    public int updateHaveScore(TeacherToCourse teacherToCourse) {
        return teacherToCourseDao.updateHaveScore(teacherToCourse);
    }

    @Override
    public int getCourseNum(TeacherToCourse teacherToCourse) {
        return teacherToCourseDao.getCourseNum(teacherToCourse);
    }
}
