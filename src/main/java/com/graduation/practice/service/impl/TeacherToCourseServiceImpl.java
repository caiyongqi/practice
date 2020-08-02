package com.graduation.practice.service.impl;

import com.graduation.practice.dao.TeacherDao;
import com.graduation.practice.dao.TeacherToCourseDao;
import com.graduation.practice.entity.TeacherToCourse;
import com.graduation.practice.service.TeacherToCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherToCourseServiceImpl implements TeacherToCourseService {
    private TeacherToCourseDao TTCDao;

    @Autowired
    public TeacherToCourseServiceImpl(TeacherToCourseDao TTCDao) {
        this.TTCDao = TTCDao;
    }

    @Override
    public List<TeacherToCourse> getAllTTC(String teacherId, int courseId) {
        return TTCDao.getAllTTC(teacherId,courseId);
    }

    @Override
    public int deleteTTC(TeacherToCourse ttc) {
        return TTCDao.deleteTTC(ttc);
    }

    @Override
    public int insert(TeacherToCourse ttc) {
        return TTCDao.insertTTC(ttc);
    }
}
