package com.graduation.practice.service;

import com.graduation.practice.entity.Teacher;
import com.graduation.practice.entity.TeacherToCourse;

import java.util.List;

public interface TeacherToCourseService {
    List<TeacherToCourse> findAllCourseByTeacher(Teacher teacher);

    int updateHaveScore(TeacherToCourse teacherToCourse);

    //by  ckl
    //得到所有已开设课
    List<TeacherToCourse> getAllTTC(String teacherId,int courseId);
    //删除课
    int deleteTTC(TeacherToCourse ttc);
    //添加课
    int insert(TeacherToCourse ttc);

}
