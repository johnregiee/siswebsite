// In a new file named GradeRepository.java
package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    // This custom method is crucial: finds all grades for a given studentId
    List<Grade> findByStudentId(Long studentId);
    Optional<Grade> findByStudentIdAndCourseCode(Long studentId, String courseCode);
    
    // New method to handle multiple results - returns list instead of optional
    List<Grade> findAllByStudentIdAndCourseCode(Long studentId, String courseCode);
    
    // Method to get the latest grade for a student-course combination
    Optional<Grade> findTopByStudentIdAndCourseCodeOrderByIdDesc(Long studentId, String courseCode);
}