package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    int id;
    private int studentId;
    String name;
    int gender;
    String email;
    int classId;
    String password;

    public Student(int studentId) {
        this.studentId = studentId;

    }
}
