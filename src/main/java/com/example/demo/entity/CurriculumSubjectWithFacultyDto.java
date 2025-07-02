package com.example.demo.entity;

public class CurriculumSubjectWithFacultyDto {
    private Long id;
    private String subjectCode;
    private String subjectName;
    private Integer units;
    private String roomNumber;
    private String time;
    private String days;
    private String facultyName;

    public CurriculumSubjectWithFacultyDto(Long id, String subjectCode, String subjectName, Integer units, String roomNumber, String time, String days, String facultyName) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.units = units;
        this.roomNumber = roomNumber;
        this.time = time;
        this.days = days;
        this.facultyName = facultyName;
    }

    public Long getId() { return id; }
    public String getSubjectCode() { return subjectCode; }
    public String getSubjectName() { return subjectName; }
    public Integer getUnits() { return units; }
    public String getRoomNumber() { return roomNumber; }
    public String getTime() { return time; }
    public String getDays() { return days; }
    public String getFacultyName() { return facultyName; }
} 