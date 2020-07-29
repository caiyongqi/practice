package com.graduation.practice.service;

import com.graduation.practice.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher findTeacherByTeacherId(Teacher teacher);
    List<Teacher> findAllTeacher();
    int saveTeacher(Teacher teacher);
    int deleteSelectedTeacher(List<String> teacherIdList);
    int deleteTeacher(String teacherId);
    int updateTeacher(Teacher teacher);
    List<Teacher> searchAllTeacherByTeacherId(String teacherKeyword, String teacherId);
}
