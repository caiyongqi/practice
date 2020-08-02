package com.graduation.practice.dao;

import com.graduation.practice.entity.StudentToScore;
import com.graduation.practice.entity.TeacherToCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StudentToScoreDao {
    int getStudentNumByCourse(TeacherToCourse teacherToCourse);
    List<StudentToScore> findAllStudentByCourse(TeacherToCourse teacherToCourse);
}
