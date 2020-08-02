package com.graduation.practice.service;

import com.graduation.practice.entity.Course;

import java.util.List;

public interface CourseService {
    int insertCourse(Course course);

    int deleteCourse(String courseId);

    int deleteSelectedCourse(List<String> ids);

    Course findCourse(String courseId);

    int updateCourse(Course course);
    //courseName为空则选取所有
    List<Course> findAllCourse(String courseName);
}
