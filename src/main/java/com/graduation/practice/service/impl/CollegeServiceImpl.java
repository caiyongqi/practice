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

    @Override
    public List<College> searchAllCollegeWithName(String collegeKeyword) {
        return collegeDao.searchAllCollegeWithName(collegeKeyword);
    }

    @Override
    public College findCollegeByName(College college) {
        return collegeDao.findCollegeByName(college);
    }

    @Override
    public int saveCollege(College college) {
        return collegeDao.saveCollege(college);
    }

    @Override
    public int deleteCollegeById(College college) {
        return collegeDao.deleteCollegeById(college);
    }

    @Override
    public College findCollegeById(College college) {
        return collegeDao.findCollegeById(college);
    }

    @Override
    public int updateCollege(College college) {
        return collegeDao.updateCollege(college);
    }

}
