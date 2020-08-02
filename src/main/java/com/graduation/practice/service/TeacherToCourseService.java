package com.graduation.practice.service;

import com.graduation.practice.entity.Teacher;
import com.graduation.practice.entity.TeacherToCourse;

import java.util.List;

public interface TeacherToCourseService {
    List<TeacherToCourse> findAllCourseByTeacher(Teacher teacher);
    int updateHaveScore(TeacherToCourse teacherToCourse);
    int getCourseNum(TeacherToCourse teacherToCourse);
}
