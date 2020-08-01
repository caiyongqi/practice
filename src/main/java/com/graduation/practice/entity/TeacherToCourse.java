package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherToCourse {
    private int teacherId;
    private int courseId;
    private Date startTime;
    private Date endTime;
    private String time;
    private Course course;
    private Teacher teacher;
//    private List<Course> courses;
    private int studentNum;

    public TeacherToCourse(int courseId, Date startTime, Date endTime) {
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
