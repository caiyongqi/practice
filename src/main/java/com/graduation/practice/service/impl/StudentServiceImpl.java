package com.graduation.practice.service.impl;

import com.graduation.practice.dao.StudentDao;
import com.graduation.practice.entity.Student;
import com.graduation.practice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student findStudentByStudentId(Student student) {
        return studentDao.findStudentByStudentId(student);
    }
}
