package com.graduation.practice.service;

import com.graduation.practice.entity.Student;
import com.graduation.practice.entity.Teacher;

import java.util.List;

public interface StudentService {
    Student findStudentByStudentId(Student student);


    List<Student> findAllStudent(String studentName, int classId);
    int insertStudent(Student student);
    int updateStudent(Student student);
    Student adminFindStudentByStudentId(String studentID);

    int deleteSelectedStudent(List<String> studentIdList);
}
