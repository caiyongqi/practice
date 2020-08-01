package com.graduation.practice.dao;

import com.graduation.practice.entity.Classes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ClassDao {
    List<Classes> findAllClass();
    Classes findClassById(@Param("id") int id);
}
