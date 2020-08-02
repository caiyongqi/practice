package com.graduation.practice.dao;

import com.graduation.practice.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TeacherDao {
    List<Teacher> findAllTeacher();

    Teacher findTeacherByTeacherId(Teacher teacher);

    int saveTeacher(Teacher teacher);
    // 批量删除
    int deleteSelectedTeacher(List<String> teacherIdList);
    // 删除单个
    int deleteTeacher(@Param("teacherId") String teacherId);
    // 更新用户
    int updateTeacher(Teacher teacher);
    // 根据账户，模糊查询
    List<Teacher> searchAllTeacherByTeacherName(@Param("teacherKeyword") String teacherKeyword);
    //
    Teacher findTeacherByName(String name);
}
