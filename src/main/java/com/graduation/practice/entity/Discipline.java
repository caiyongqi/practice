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
    private College college;

    public Discipline(String name, String desc, int collegeId) {
        this.name = name;
        this.desc = desc;
        this.collegeId = collegeId;
    }

    public Discipline(int id) {
        this.id = id;
    }

    public Discipline(int id, String name, String desc, int collegeId) {
        this.name = name;
        this.desc = desc;
        this.collegeId = collegeId;
        this.id = id;
    }
}
