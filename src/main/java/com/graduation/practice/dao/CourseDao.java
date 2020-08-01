package com.graduation.practice.dao;

import com.graduation.practice.entity.Classes;
import com.graduation.practice.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CourseDao {
    int insertCourse(Course course);

    int deleteCourse(String courseId);

    int deleteSelectedCourse(List<String> ids) ;

    Course findCourse(String courseId);

    int updateCourse(Course course);

    List<Course> findAllCourse(String courseName);
}