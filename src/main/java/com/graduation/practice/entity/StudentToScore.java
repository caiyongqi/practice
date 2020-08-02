package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentToScore {
    private int studentId;
    private int courseId;
    private float score;
    private Date startTime;
    private Date endTime;
    private String address;
    private Student student;

    public StudentToScore(int courseId, Date startTime, Date endTime) {
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public StudentToScore(Integer studentId, Float score) {
        this.studentId = studentId;
        this.score = score;
    }
}
