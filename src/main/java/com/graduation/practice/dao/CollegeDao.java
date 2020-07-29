package com.graduation.practice.dao;

import com.graduation.practice.entity.College;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CollegeDao {
    List<College> findAllCollege();
}

