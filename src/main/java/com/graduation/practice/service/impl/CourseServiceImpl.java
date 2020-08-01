package com.graduation.practice.service.impl;

import com.graduation.practice.dao.CourseDao;
import com.graduation.practice.entity.Course;
import com.graduation.practice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    //private CourseDao courseDao;
    @Autowired
    CourseDao courseDao;


    @Override
    public int insertCourse(Course course) {
        return courseDao.insertCourse(course);
    }

    @Override
    public int deleteCourse(String courseId) {
        return courseDao.deleteCourse(courseId);
    }

    @Override
    public int deleteSelectedCourse(List<String> ids) {
        return courseDao.deleteSelectedCourse(ids);
    }

    @Override
    public Course findCourse(String courseId) {
        return courseDao.findCourse(courseId);
    }

    @Override
    public int updateCourse(Course course) {
        return courseDao.updateCourse(course);
    }

    @Override
    public List<Course> findAllCourse(String courseName) {
        return courseDao.findAllCourse(courseName);
    }
}
