package com.graduation.practice.service;


import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.Classes;
import com.graduation.practice.entity.Counselor;
import com.graduation.practice.entity.Student;
import com.graduation.practice.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounselorService {
    List<Student> findAllStudent(User user);

    List<Student> searchAllStudentById(String counselorStudentKeyword, String account);

    Student findStudentByStudentId(Student student);

    int updateStudentPassword(User user);

    Counselor findCounselorByCounselorId(Counselor counselor);

    List<Classes> findClassByCounselorId(User user);
    int insertStudent(Student student);
    int updateStudent(Student student);
}
