package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreShow {
//    private int studentId;
    private int courseId;
    private float score;
    private Date startTime;
    private Date endTime;
    private float credit;
    private String name;
    private String desc;
}
