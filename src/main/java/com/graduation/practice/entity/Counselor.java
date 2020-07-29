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
}
