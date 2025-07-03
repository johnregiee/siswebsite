package com.example.demo.controller;

public class EnrollmentWithDetailsDto {
    private final String subjectCode;
    private final String subjectName;
    private final Integer units;
    private final String facultyName;
    private final String roomNumber;
    private final String time;
    private final String days;
    public EnrollmentWithDetailsDto(String subjectCode, String subjectName, Integer units, String facultyName, String roomNumber, String time, String days) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.units = units;
        this.facultyName = facultyName;
        this.roomNumber = roomNumber;
        this.time = time;
        this.days = days;
    }
    public String getSubjectCode() { return subjectCode; }
    public String getSubjectName() { return subjectName; }
    public Integer getUnits() { return units; }
    public String getFacultyName() { return facultyName; }
    public String getRoomNumber() { return roomNumber; }
    public String getTime() { return time; }
    public String getDays() { return days; }
} 