package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private String studentId;
    private String name;
    private int age;
    private int gender;
    private String address;
    private String email;
    private String phoneNumber;
    private int classId;
    private String photoUrl;
    private Date date;

    private Classes classes;

    public Student(String studentId){
        this.studentId = studentId;
    }
    public Student(String studentId,String name,int gender,int classId,Date date){
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.classId = classId;
        this.date = date;
    }


    public Student(String studentId, String name, int age, int gender, String address, String email, String phoneNumber, int classId, String photoUrl, Date date) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.classId = classId;
        this.photoUrl = photoUrl;
        this.date = date;
    }

    public Student(int id) {
        this.id = id;
    }
}
