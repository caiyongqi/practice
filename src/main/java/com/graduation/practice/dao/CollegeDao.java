package com.graduation.practice.dao;

import com.graduation.practice.entity.College;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CollegeDao {
    List<College> findAllCollege();
    List<College> searchAllCollegeWithName(@Param("collegeKeyword") String collegeKeyword);
    College findCollegeByName(College college);

    int saveCollege(College college);

    int deleteCollegeById(College college);

    College findCollegeById(College college);

    int updateCollege(College college);
}

