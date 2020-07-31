package com.graduation.practice.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TeacherToCourseDao {
    int getCourseNumByTeacherId(@Param("teacherId") String teacherId);
}
