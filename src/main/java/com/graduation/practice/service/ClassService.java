package com.graduation.practice.service;

import com.graduation.practice.entity.ClassInfo;
import com.graduation.practice.entity.Classes;
import com.graduation.practice.entity.Discipline;

import java.util.List;

public interface ClassService {
    List<Classes> findAllClass();
    Classes findClassById(int id);

    List<ClassInfo> findAllClassInfo();

    int deleteClass(List classIds);

    int addClassInfo(Classes classes);

    int updateClassInfo(Classes classes);

    List<ClassInfo> searchClassByName(String classKeyword);
    List<ClassInfo> findAllClassInfoByDiscipline(Discipline discipline);
}
