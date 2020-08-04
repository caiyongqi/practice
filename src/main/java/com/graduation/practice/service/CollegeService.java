package com.graduation.practice.service;

import com.graduation.practice.entity.College;

import java.util.List;

public interface CollegeService {
    List<College> findAllCollege();
    List<College> searchAllCollegeWithName(String collegeKeyword);

    College findCollegeByName(College college);

    int saveCollege(College college);

    int deleteCollegeById(College college);

    College findCollegeById(College college);

    int updateCollege(College college);
}
