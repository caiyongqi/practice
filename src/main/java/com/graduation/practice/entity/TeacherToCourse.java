package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherToCourse {
    private int teacherId;
    private int courseId;
    private Date startTime;
    private Date endTime;
}
