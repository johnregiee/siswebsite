package com.example.demo.entity;

import jakarta.persistence.*;
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
    private String day;       // e.g., "Monday", "Tue-Thu"
    private String time;      // e.g., "9:00 AM - 10:30 AM"
    private String room;
}
