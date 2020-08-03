package com.graduation.practice.entity;

// 用于学生界面信息展示

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SutdentInfoShow {
    private String studentId;
    private String studentName;
    private int age;
    private int address;
    private String email;
    private String phoneNumber;
    private String className;
    private Date date; // 注册时间

}
