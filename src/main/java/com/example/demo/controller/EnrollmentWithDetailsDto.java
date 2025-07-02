package com.example.demo.controller;

public class EnrollmentWithDetailsDto {
    private final String subjectCode;
    private final String subjectName;
    private final Integer units;
    private final String facultyName;
    public EnrollmentWithDetailsDto(String subjectCode, String subjectName, Integer units, String facultyName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.units = units;
        this.facultyName = facultyName;
    }
    public String getSubjectCode() { return subjectCode; }
    public String getSubjectName() { return subjectName; }
    public Integer getUnits() { return units; }
    public String getFacultyName() { return facultyName; }
} 