// In a new file named GradeService.java
package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Grade;
import com.example.demo.repository.GradeRepository;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> getGradesByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }
    
    public java.util.Optional<Grade> getGradeByStudentIdAndCourseCode(Long studentId, String courseCode) {
        return gradeRepository.findByStudentIdAndCourseCode(studentId, courseCode);
    }
    
    // New method to get all grades for a student-course combination
    public List<Grade> getAllGradesByStudentIdAndCourseCode(Long studentId, String courseCode) {
        return gradeRepository.findAllByStudentIdAndCourseCode(studentId, courseCode);
    }
    
    // Method to get the latest grade for a student-course combination
    public java.util.Optional<Grade> getLatestGradeByStudentIdAndCourseCode(Long studentId, String courseCode) {
        return gradeRepository.findTopByStudentIdAndCourseCodeOrderByIdDesc(studentId, courseCode);
    }
    
    // We will need this later for Admins/Faculty to add grades
    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }
    
    // Method to delete all grades for a student-course combination (useful for cleanup)
    public void deleteAllGradesByStudentIdAndCourseCode(Long studentId, String courseCode) {
        List<Grade> grades = gradeRepository.findAllByStudentIdAndCourseCode(studentId, courseCode);
        gradeRepository.deleteAll(grades);
    }
}