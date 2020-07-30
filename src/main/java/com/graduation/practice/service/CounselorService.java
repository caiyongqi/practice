package com.graduation.practice.service;


import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.Counselor;
import com.graduation.practice.entity.Student;
import com.graduation.practice.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounselorService {
    List<Student> findAllStudent(User user);

    List<Student> searchAllStudentById(String keyword, String account);

    Student findStudentByStudentId(Student student);

    int updateStudent(User user);

}
