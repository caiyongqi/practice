package com.graduation.practice.service.impl;

import com.graduation.practice.dao.TeacherDao;
import com.graduation.practice.dao.TeacherToCourseDao;
import com.graduation.practice.entity.Teacher;
import com.graduation.practice.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherDao teacherDao;
    private TeacherToCourseDao teacherToCourseDao;
    // 不能忘记添加@Autowired
    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao, TeacherToCourseDao teacherToCourseDao) {
        this.teacherDao = teacherDao;
        this.teacherToCourseDao = teacherToCourseDao;
    }

    public TeacherServiceImpl() {}

    @Override
    public Teacher findTeacherByTeacherId(Teacher Teacher) {
        return teacherDao.findTeacherByTeacherId(Teacher);
    }

    @Override
    public List<Teacher> findAllTeacher() {
        return teacherDao.findAllTeacher();
    }

    @Override
    public int saveTeacher(Teacher teacher) {
        return teacherDao.saveTeacher(teacher);
    }

    @Override
    public int deleteSelectedTeacher(List<String> teacherIdList) {
        return teacherDao.deleteSelectedTeacher(teacherIdList);
    }

    @Override
    public int deleteTeacher(String teacherId) {
        return teacherDao.deleteTeacher(teacherId);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return teacherDao.updateTeacher(teacher);
    }

    @Override
    public List<Teacher> searchAllTeacherByTeacherName(String teacherKeyword) {
        return teacherDao.searchAllTeacherByTeacherName(teacherKeyword);
    }

    @Override
    public int getCourseNumByTeacherId(String teacherId) {
        return teacherToCourseDao.getCourseNumByTeacherId(teacherId);
    }

    @Override
    public Teacher findTeacherByName(String name) {
        return teacherDao.findTeacherByName(name);
    }
}
