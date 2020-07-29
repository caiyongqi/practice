package com.graduation.practice.service;

import com.graduation.practice.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAllStudent();
    int insertStudent(Student student);
}
