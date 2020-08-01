package com.graduation.practice.dao;

import com.graduation.practice.entity.Classes;
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
    Counselor findCounselorByCounselorId(Counselor counselor);

    Student findStudentByStudentId(Student student);
    int updateStudentPassword(User user);

    List<Classes> findClassByCounselorId(User user);
    int insertStudent(Student student);
    int updateStudent(Student student);
    List<Counselor> findAllCounselor();
    int saveCounselor(Counselor counselor);
    int deleteSelectedCounselor(List<String> counselorIdList);
    List<Counselor> searchAllCounselorByCounselorName(String Keyword);
    int updateCounselor(Counselor counselor);
    Counselor findCounselorProfileByCounselorId(Counselor counselor);
}
