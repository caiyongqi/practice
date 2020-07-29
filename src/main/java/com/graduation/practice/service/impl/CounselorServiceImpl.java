package com.graduation.practice.service.impl;

import com.github.pagehelper.PageInfo;
import com.graduation.practice.dao.CounselorDao;
import com.graduation.practice.dao.UserDao;
import com.graduation.practice.entity.Counselor;
import com.graduation.practice.entity.Student;
import com.graduation.practice.entity.User;
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
    public List<Student> searchAllStudentById(String keyword, String account) {
        return counselorDao.searchAllStudentById(keyword,account);
    }
}
