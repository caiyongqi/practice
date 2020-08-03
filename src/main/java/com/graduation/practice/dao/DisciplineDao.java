package com.graduation.practice.dao;

import com.graduation.practice.entity.College;
import com.graduation.practice.entity.Discipline;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DisciplineDao {
    List<Discipline> findAllDiscipline();
    List<Discipline> findAllDisciplineByCollege(College college);
    List<Discipline> findAllDisciplineAndCollege();
    Discipline findDisciplineByName(Discipline discipline);
    int saveDiscipline(Discipline discipline);
    int deleteDisciplineById(Discipline id);
    Discipline findDisciplineById(Discipline discipline);
    int updateDiscipline(Discipline discipline);
}
