package com.graduation.practice.service;


import com.github.pagehelper.PageInfo;
import com.graduation.practice.entity.*;
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

    List<Counselor> findAllCounselor();
    int saveCounselor(Counselor counselor);

    int deleteSelectedCounselor(List<String> counselorIdList);
    int deleteCounselor(String counselorId);
    int updateCounselor(Counselor counselor);

    //int getCourseNumByTeacherId(String teacherId);
    List<Counselor> searchAllCounselorByCounselorName(String Keyword);
    Counselor findCounselorProfileByCounselorId(Counselor counselor);
    //===辅导员查询学生成绩
    List<ScoreShow> findScoreByStudent04(Student student);

}
