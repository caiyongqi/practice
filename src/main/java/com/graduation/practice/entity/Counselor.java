package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Counselor {
    private int id;
    private String counselorId;
    private String name;
    private int age;
    private int gender;
    private String address;
    private String email;
    private String phoneNumber;
    private int disciplineId;
    private String photoUrl;
    private Discipline discipline;
    private College college;

    public Counselor(String counselorId) {
        this.counselorId = counselorId;
    }
    public Counselor(String counselorId,String name,int gender,int disciplineId){
        this.counselorId = counselorId;
        this.name = name;
        this.gender = gender;
        this.disciplineId = disciplineId;
    }
    public Counselor(String counselorId, String name, int age, int gender, String address, String email, String phoneNumber, int disciplineId, String photoUrl){
        this.counselorId = counselorId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.disciplineId = disciplineId;
        this.photoUrl = photoUrl;
    }
}
