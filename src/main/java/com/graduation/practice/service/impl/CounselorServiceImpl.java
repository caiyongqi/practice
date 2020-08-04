package com.graduation.practice.service.impl;

import com.github.pagehelper.PageInfo;
import com.graduation.practice.dao.CounselorDao;
import com.graduation.practice.dao.UserDao;
import com.graduation.practice.entity.*;
import com.graduation.practice.service.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounselorServiceImpl implements CounselorService {
    private CounselorDao counselorDao;

    @Autowired
    public CounselorServiceImpl(CounselorDao counselorDao) {
        this.counselorDao = counselorDao;
    }
    public CounselorServiceImpl() {}

    @Override
    public List<Student> findAllStudent(User user) {
        return counselorDao.findAllStudent(user);
    }

    @Override
    public List<Student> searchAllStudentById(String counselorStudentKeyword, String account) {
        return counselorDao.searchAllStudentById(counselorStudentKeyword,account);
    }

    @Override
    public Student findStudentByStudentId(Student student) {
        return counselorDao.findStudentByStudentId(student);
    }

    @Override
    public int updateStudentPassword(User user) {
        return counselorDao.updateStudentPassword(user);
    }

    @Override
    public Counselor findCounselorByCounselorId(Counselor counselor) {
        return counselorDao.findCounselorByCounselorId(counselor);
    }

    @Override
    public List<Classes> findClassByCounselorId(User user) {
        return counselorDao.findClassByCounselorId(user);
    }

    @Override
    public int insertStudent(Student student) {
        return counselorDao.insertStudent(student);
    }

    @Override
    public int updateStudent(Student student) {
        return counselorDao.updateStudent(student);
    }

    @Override
    public List<Counselor> findAllCounselor() {
        return counselorDao.findAllCounselor();
    }

    @Override
    public int saveCounselor(Counselor counselor) {
        return counselorDao.saveCounselor(counselor);
    }

    @Override
    public int deleteSelectedCounselor(List<String> counselorIdList) {
        return counselorDao.deleteSelectedCounselor(counselorIdList);
    }

    @Override
    public int deleteCounselor(String counselorId) {
        return counselorDao.deleteCounselor(counselorId);
    }

    @Override
    public int updateCounselor(Counselor counselor) {
        return counselorDao.updateCounselor(counselor);
    }

    @Override
    public List<Counselor> searchAllCounselorByCounselorName(String Keyword) {
        return counselorDao.searchAllCounselorByCounselorName(Keyword);
    }

    @Override
    public Counselor findCounselorProfileByCounselorId(Counselor counselor) {
        return counselorDao.findCounselorProfileByCounselorId(counselor);
    }


    //======辅导员查询学生成绩

    @Override
    public List<ScoreShow> findScoreByStudent04(Student student) {
        // 获取学生分数信息
        List<ScoreShow> scoreShows = counselorDao.findStudentScore04(student);
        System.out.println(scoreShows);
//        System.out.println(course);
        return scoreShows;
    }
}

