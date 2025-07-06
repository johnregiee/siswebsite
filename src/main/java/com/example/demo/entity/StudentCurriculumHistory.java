package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student_curriculum_history")
public class StudentCurriculumHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long curriculumId;
    private LocalDate startDate;
    private LocalDate endDate; // nullable for current

    public StudentCurriculumHistory() {}
    public StudentCurriculumHistory(Long studentId, Long curriculumId, LocalDate startDate, LocalDate endDate) {
        this.studentId = studentId;
        this.curriculumId = curriculumId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getCurriculumId() { return curriculumId; }
    public void setCurriculumId(Long curriculumId) { this.curriculumId = curriculumId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
} 