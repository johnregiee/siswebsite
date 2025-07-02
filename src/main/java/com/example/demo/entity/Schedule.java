package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private String courseCode;
    private String courseName;
    private String subjectCode;
    private String subjectName;
    private String day;       // e.g., "Monday", "Tue-Thu"
    private String time;      // e.g., "9:00 AM - 10:30 AM"
    private String room;

      @Column(name = "faculty_id")
    private Long facultyId; 

    private Long curriculumId;
    private Long subjectId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public Long getFacultyId() { return facultyId; }
    public void setFacultyId(Long facultyId) { this.facultyId = facultyId; }
    public Long getCurriculumId() { return curriculumId; }
    public void setCurriculumId(Long curriculumId) { this.curriculumId = curriculumId; }
    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
}
