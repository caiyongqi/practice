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
    public List<TeacherToCourse> getAllTTC(String teacherId, int courseId) {
        return teacherToCourseDao.getAllTTC(teacherId,courseId);
    }

    @Override
    public int deleteTTC(TeacherToCourse ttc) {
        return teacherToCourseDao.deleteTTC(ttc);
    }

    @Override
    public int insert(TeacherToCourse ttc) {
        return teacherToCourseDao.insertTTC(ttc);
    }
}
