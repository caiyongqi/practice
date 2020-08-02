package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discipline {
    private int id;
    private String name;
    private String desc;
    private int collegeId;

}
