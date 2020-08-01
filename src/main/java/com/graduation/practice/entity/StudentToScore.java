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

    public StudentToScore(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }
}
