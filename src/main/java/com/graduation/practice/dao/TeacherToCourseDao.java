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

    //开课管理by ckl
    //得到所有已开课程，teacherId与courseId不传表示无其限制
    List<TeacherToCourse> getAllTTC(@Param("teacherId") String teacherId,@Param("courseId") int courseId);
    //删除课程
    int deleteTTC(TeacherToCourse ttc);
    //添加课程
    int insertTTC(TeacherToCourse ttc);
}
