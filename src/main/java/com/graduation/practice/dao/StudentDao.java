package com.graduation.practice.dao;

import com.graduation.practice.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StudentDao {
    //找到所有学生信息
    List<Student> findAllStudent();
    int insertStudent(Student student);
}
