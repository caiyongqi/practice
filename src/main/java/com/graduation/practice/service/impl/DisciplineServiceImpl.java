package com.graduation.practice.service.impl;

import com.graduation.practice.dao.DisciplineDao;
import com.graduation.practice.entity.College;
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

    @Override
    public List<Discipline> findAllDisciplineByCollege(College college) {
        return disciplineDao.findAllDisciplineByCollege(college);
    }

    @Override
    public List<Discipline> findAllDisciplineAndCollege() {
        return disciplineDao.findAllDisciplineAndCollege();
    }

    @Override
    public Discipline findDisciplineByName(Discipline discipline) {
        return disciplineDao.findDisciplineByName(discipline);
    }

    @Override
    public int saveDiscipline(Discipline discipline) {
        return disciplineDao.saveDiscipline(discipline);
    }

    @Override
    public int deleteDisciplineById(Discipline discipline) {
        return disciplineDao.deleteDisciplineById(discipline);
    }

    @Override
    public Discipline findDisciplineById(Discipline discipline) {
        return disciplineDao.findDisciplineById(discipline);
    }

    @Override
    public int updateDiscipline(Discipline discipline) {
        return disciplineDao.updateDiscipline(discipline);
    }

}
