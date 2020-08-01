package com.graduation.practice.service;

import com.graduation.practice.entity.Classes;

import java.util.List;

public interface ClassService {
    List<Classes> findAllClass();
    Classes findClassById(int id);
}
