package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseCode(String courseCode);

    // Find active enrollment for a student and subject
    @Query("SELECT e FROM Enrollment e WHERE e.studentId = :studentId AND e.courseCode = :courseCode AND (e.status IS NULL OR LOWER(e.status) NOT IN ('deleted', 'dropped'))")
    List<Enrollment> findActiveEnrollment(Long studentId, String courseCode);

    @Query("SELECT e FROM Enrollment e WHERE e.studentId = :studentId AND EXISTS (SELECT 1 FROM Subject s WHERE s.subjectCode = e.courseCode)")
    List<Enrollment> findExistingSubjectEnrollmentsByStudentId(Long studentId);
}
