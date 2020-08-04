package com.graduation.practice.dao;

import com.graduation.practice.entity.ClassInfo;
import com.graduation.practice.entity.Classes;
import com.graduation.practice.entity.Discipline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ClassDao {
    List<Classes> findAllClass();
    Classes findClassById(@Param("id") int id);

    List<ClassInfo> findAllClassInfo();

    int deleteClass(List list);

    int addClass(Classes classes);

    int updateClassInfo(Classes classes);

    List<ClassInfo> searchClassByName(@Param("classKeyword") String classKeyword);

    List<ClassInfo> findAllClassInfoByDiscipline(Discipline discipline);
}
