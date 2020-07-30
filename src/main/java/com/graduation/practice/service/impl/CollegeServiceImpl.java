package com.graduation.practice.service.impl;

import com.graduation.practice.dao.CollegeDao;
import com.graduation.practice.entity.College;
import com.graduation.practice.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {
    private CollegeDao collegeDao;
    @Autowired
    public CollegeServiceImpl(CollegeDao collegeDao) {
        this.collegeDao = collegeDao;
    }

    @Override
    public List<College> findAllCollege() {
        return collegeDao.findAllCollege();
    }
}
