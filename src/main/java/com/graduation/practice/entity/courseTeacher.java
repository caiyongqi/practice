package com.graduation.practice.entity;

/*
 * 选课信息的整合
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class courseTeacher {
    private String teacherName;
    private String courseName;
    private String studentId;
    private String courseId;
    private Date startTime;
    private Date endTime;
    private String time;
}
