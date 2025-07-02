package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurriculumSubjectWithFacultyDto {
    private Long id;
    private String courseCode;
    private String courseName;
    private String subjectCode;
    private String subjectName;
    private int units;
    private String roomNumber;
    private String time;
    private String days;
    private String facultyName;

    public CurriculumSubjectWithFacultyDto(Long id, String courseCode, String courseName, String subjectCode, String subjectName, int units, String roomNumber, String time, String days, String facultyName) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.units = units;
        this.roomNumber = roomNumber;
        this.time = time;
        this.days = days;
        this.facultyName = facultyName;
    }
} 