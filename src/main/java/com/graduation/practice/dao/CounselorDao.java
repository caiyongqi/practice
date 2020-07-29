package com.graduation.practice.dao;

import com.graduation.practice.entity.Counselor;
import com.graduation.practice.entity.Student;
import com.graduation.practice.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CounselorDao {
    int getTotal();
    List<Student> findAllStudent(User user);
    List<Student> searchAllStudentById(@Param("keyword") String keyword, @Param("account") String account);

}
