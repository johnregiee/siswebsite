package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Enrollment;
import com.example.demo.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByStudentId(Long studentId);

    List<Enrollment> findByCourseCode(String courseCode);
    List<Schedule> findByFacultyId(Long facultyId);
}
