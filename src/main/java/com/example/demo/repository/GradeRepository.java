// In a new file named GradeRepository.java
package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    // This custom method is crucial: finds all grades for a given studentId
    List<Grade> findByStudentId(Long studentId);
}