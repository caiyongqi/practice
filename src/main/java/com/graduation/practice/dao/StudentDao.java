package com.graduation.practice.dao;

import com.graduation.practice.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StudentDao {
//    Student findStudentByAccount(Student student);
    Student findStudentByStudentId(Student student);


    // 学生登录
    User findUserByAccount(User user);
    // 获取学生的登录信息
    Student findStudentInfo(User user);

    // 获取学生分数信息
    List<ScoreShow> findStudentScore(User user);
    // 获取课程信息
//    List<Course> findCourseInfo(List<Integer> list);

    //找到所有学生信息
    List<Student> findAllStudent(@Param("studentName") String studentName, @Param("classId") int classId);
    int insertStudent(Student student);
    int updateStudent(Student student);
    Student adminFindStudentByStudentId(String student);

    // 批量删除
    int deleteSelectedStudent(List<String> studentIdList);

    Student findStudentById(Student student);

    // 课表查询
    List<courseTeacher> findCourseByTeacher(User user);

    Student findStudentInfoByUser(User user);

    // 课程查询
    List<courseTeacher> findCourseByAllTeacher(User user);

    // 添加课程逻辑处理
    int insertCourseToStudent(StudentToScore studentToScore);

    int updateUserPassword(@Param("account") String account, @Param("repassword") String repassword);
}
