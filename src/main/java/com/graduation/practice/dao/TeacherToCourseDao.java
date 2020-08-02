package com.graduation.practice.dao;

import com.graduation.practice.entity.Teacher;
import com.graduation.practice.entity.TeacherToCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TeacherToCourseDao {
    int getCourseNumByTeacherId(@Param("teacherId") String teacherId);
    // 根据老师信息查询出所授课程信息
    List<TeacherToCourse> findAllCourseByTeacher(Teacher teacher);
    // 设置为已上传分数
    int updateHaveScore(TeacherToCourse teacherToCourse);
}
