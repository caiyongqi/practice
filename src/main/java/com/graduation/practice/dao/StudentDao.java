package com.graduation.practice.dao;

import com.graduation.practice.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StudentDao {
    Student findStudentByStudentId(Student student);
}
