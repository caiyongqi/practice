package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private int id;
    private String teacherId;
    private String name;
    private int age;
    private int gender;
    private String address;
    private String email;
    private String phoneNumber;
    private int collegeId;
    private String photoUrl;
    private College college;
    public Teacher(String teacherId) {
        this.teacherId = teacherId;
    }
    public Teacher(String teacherId, String name, int gender, int collegeId){
        this.teacherId = teacherId;
        this.name = name;
        this.gender = gender;
        this.collegeId = collegeId;
    }

    public Teacher(String teacherId, String name, int age, int gender, String address, String email, String phoneNumber, int collegeId, String photoUrl) {
        this.teacherId = teacherId;
        this.name = name;
        this.gender = gender;
        this.collegeId = collegeId;
        this.age = age;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
    }
}
