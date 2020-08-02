package com.graduation.practice.service;

import com.graduation.practice.entity.Course;

import java.util.List;

public interface CourseService {
    int insertCourse(Course course);

    int deleteCourse(String courseId);

    int deleteSelectedCourse(List<String> ids);

    Course findCourse(String courseId);

    int updateCourse(Course course);

    List<Course> findAllCourse(String courseName);
}
