package com.graduation.practice.service.impl;

import com.graduation.practice.dao.StudentDao;
import com.graduation.practice.entity.Student;
import com.graduation.practice.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao;

    @Override
    public Student findUserByAccount(Student student) {
        return studentDao.findStudentByAccount(student);
    }
}
