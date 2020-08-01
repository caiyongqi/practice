package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String education;
    private String desc;
    // 一个教师对应多个课程，课程可以相同但时间不同
    private List<TeacherToCourse> teacherToCourses;

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

    public Teacher(String teacherId, String name, int age, int gender, String address, String email, String phoneNumber, String photoUrl, String education, String desc) {
        this.teacherId = teacherId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.desc = desc;
        this.education = education;
    }
}
