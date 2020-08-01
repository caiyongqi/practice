package com.graduation.practice.service.impl;

import com.graduation.practice.dao.StudentDao;
import com.graduation.practice.entity.Student;
import com.graduation.practice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Student> findAllStudent(String studentName, int classId) {
        return studentDao.findAllStudent(studentName,classId);
    }

    @Override
    public int insertStudent(Student student) {
        return studentDao.insertStudent(student);
    }

    @Override
    public int updateStudent(Student student) {
        return studentDao.updateStudent(student);
    }

    @Override
    public Student adminFindStudentByStudentId(String studentID) {
        return studentDao.adminFindStudentByStudentId(studentID);
    }

    @Override
    public int deleteSelectedStudent(List<String> studentIdList) {
        return studentDao.deleteSelectedStudent(studentIdList);
    }

    @Override
    public Student findStudentById(Student student) {
        return studentDao.findStudentById(student);
    }
}
