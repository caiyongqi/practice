package com.graduation.practice.service.impl;

import com.graduation.practice.dao.ClassDao;
import com.graduation.practice.entity.Classes;
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

}
