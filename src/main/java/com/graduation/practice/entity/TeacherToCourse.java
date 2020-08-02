package com.graduation.practice.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherToCourse {
    private String teacherId;
    private int courseId;
    private Date startTime;
    private Date endTime;
    private String time;

    public TeacherToCourse(String teacherId, int courseId, Date startTime, Date endTime) {
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
    }


}
