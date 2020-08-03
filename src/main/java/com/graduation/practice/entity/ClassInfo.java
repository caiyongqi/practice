package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//课程信息的展示
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassInfo {
    private int id;
    private String className;
    private String disciplineName;
//    private int disciplinedId;
    private String collegeName;
//    private String address;
}
