package com.graduation.practice.service;

import com.graduation.practice.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherService {
    Teacher findTeacherByTeacherId(Teacher teacher);
    List<Teacher> findAllTeacher();
    int saveTeacher(Teacher teacher);
    int deleteSelectedTeacher(List<String> teacherIdList);
    int deleteTeacher(String teacherId);
    int updateTeacher(Teacher teacher);
    List<Teacher> searchAllTeacherByTeacherName(String teacherKeyword);
    int getCourseNumByTeacherId(String teacherId);
}
