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
}
