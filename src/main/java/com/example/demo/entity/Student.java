package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentNumber;
    private String name;
    private String email;
    private String password;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "section_id", nullable = true)
    private Section section;

    private Long curriculumId;
    public Long getCurriculumId() { return curriculumId; }
    public void setCurriculumId(Long curriculumId) { this.curriculumId = curriculumId; }
}
