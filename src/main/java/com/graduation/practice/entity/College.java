package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class College {
    private int id;
    private String name;
    private String desc;
    private String address;
    private List<Teacher> teachers;

    public College(String name, String desc, String address) {
        this.name = name;
        this.address = address;
        this.desc = desc;
    }

    public College(int id) {
        this.id = id;
    }

    public College(int id, String name, String desc, String address) {
        this.name = name;
        this.address = address;
        this.desc = desc;
        this.id = id;
    }
}
