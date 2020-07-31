package com.graduation.practice.dao;

import com.graduation.practice.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StudentDao {
    Student findStudentByStudentId(Student student);
    //找到所有学生信息
    List<Student> findAllStudent(@Param("studentName") String studentName, @Param("classId") int classId);
    int insertStudent(Student student);
    int updateStudent(Student student);
    Student adminFindStudentByStudentId(String student);

    // 批量删除
    int deleteSelectedStudent(List<String> studentIdList);
}
