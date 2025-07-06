package com.example.demo.repository;

import com.example.demo.entity.StudentCurriculumHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentCurriculumHistoryRepository extends JpaRepository<StudentCurriculumHistory, Long> {
    List<StudentCurriculumHistory> findByStudentIdOrderByStartDateAsc(Long studentId);
} 