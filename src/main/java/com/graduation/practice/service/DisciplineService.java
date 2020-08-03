package com.graduation.practice.service;

import com.graduation.practice.entity.College;
import com.graduation.practice.entity.Discipline;

import java.util.List;

public interface DisciplineService {
    List<Discipline> findAllDiscipline();

    List<Discipline> findAllDisciplineByCollege(College college);

    List<Discipline> findAllDisciplineAndCollege();

    Discipline findDisciplineByName(Discipline discipline);

    int saveDiscipline(Discipline discipline);

    int deleteDisciplineById(Discipline discipline);

    Discipline findDisciplineById(Discipline discipline);

    int updateDiscipline(Discipline discipline);
}
