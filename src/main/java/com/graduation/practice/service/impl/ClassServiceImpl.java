package com.graduation.practice.service.impl;

import com.graduation.practice.dao.ClassDao;
import com.graduation.practice.entity.ClassInfo;
import com.graduation.practice.entity.Classes;
import com.graduation.practice.entity.Discipline;
import com.graduation.practice.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassDao classDao;

    @Override
    public List<Classes> findAllClass() {
        return classDao.findAllClass();
    }

    @Override
    public Classes findClassById(int id) {
        return classDao.findClassById(id);
    }

    // 组合信息查询
    @Override
    public List<ClassInfo> findAllClassInfo() {
        return classDao.findAllClassInfo();
    }

    // 删除
    @Override
    public int deleteClass(List classIds) {
        return classDao.deleteClass(classIds);
    }

    @Override
    public int addClassInfo(Classes classes) {
        return  classDao.addClass(classes);
    }

    @Override
    public int updateClassInfo(Classes classes) {
        return classDao.updateClassInfo(classes);
    }

    @Override
    public List<ClassInfo> searchClassByName(String classKeyword) {
        return classDao.searchClassByName(classKeyword);
    }

    @Override
    public List<ClassInfo> findAllClassInfoByDiscipline(Discipline discipline) {
        return classDao.findAllClassInfoByDiscipline(discipline);
    }

}
