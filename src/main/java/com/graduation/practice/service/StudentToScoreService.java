package com.graduation.practice.service;

import com.graduation.practice.entity.StudentToScore;
import com.graduation.practice.entity.TeacherToCourse;

import java.util.List;

public interface StudentToScoreService {
    int getStudentNumByCourse(TeacherToCourse teacherToCourse);
    List<StudentToScore> findAllStudentByCourse(TeacherToCourse teacherToCourse);
}
