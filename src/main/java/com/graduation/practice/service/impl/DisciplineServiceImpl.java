package com.graduation.practice.service.impl;

import com.graduation.practice.dao.DisciplineDao;
import com.graduation.practice.entity.Discipline;
import com.graduation.practice.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineServiceImpl implements DisciplineService{
    private DisciplineDao disciplineDao;
    @Autowired
    public DisciplineServiceImpl(DisciplineDao disciplineDao){
        this.disciplineDao = disciplineDao;
    }

    public List<Discipline> findAllDiscipline(){
        return disciplineDao.findAllDiscipline();
    }

}
