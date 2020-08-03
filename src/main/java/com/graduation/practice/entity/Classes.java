package com.graduation.practice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Classes {
    private int id;
    private String name;
    private int disciplinedId;

    public Classes(int classId, String className, int disciplineId) {
        this.id = classId;
        this.name = className;
        this.disciplinedId = disciplineId;
    }

}
