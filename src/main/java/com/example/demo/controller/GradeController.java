// In a new file named GradeController.java
package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Grade;
import com.example.demo.service.GradeService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    // The endpoint our frontend will call
    @GetMapping("/student/{studentId}")
    public List<Grade> getGradesByStudentId(@PathVariable Long studentId) {
        return gradeService.getGradesByStudentId(studentId);
    }
    
    // The endpoint for Admins/Faculty to post new grades
    @PostMapping
    public ResponseEntity<?> addOrUpdateGrade(@RequestBody Grade grade) {
        try {
            // Check if there are multiple grades for this student-course combination
            List<Grade> existingGrades = gradeService.getAllGradesByStudentIdAndCourseCode(
                grade.getStudentId(), grade.getCourseCode());
            
            if (existingGrades.size() > 1) {
                // If there are duplicates, delete all existing grades and create a new one
                gradeService.deleteAllGradesByStudentIdAndCourseCode(grade.getStudentId(), grade.getCourseCode());
                Grade savedGrade = gradeService.saveGrade(grade);
                return ResponseEntity.ok(savedGrade);
            } else if (existingGrades.size() == 1) {
                // Update the existing grade
                Grade existing = existingGrades.get(0);
                existing.setGrade(grade.getGrade());
                existing.setCourseName(grade.getCourseName());
                existing.setSemester(grade.getSemester());
                existing.setComments(grade.getComments());
                Grade savedGrade = gradeService.saveGrade(existing);
                return ResponseEntity.ok(savedGrade);
            } else {
                // Create new grade
                Grade savedGrade = gradeService.saveGrade(grade);
                return ResponseEntity.ok(savedGrade);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving grade: " + e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}/course/{courseCode}")
    public ResponseEntity<?> getGradeByStudentIdAndCourseCode(@PathVariable Long studentId, @PathVariable String courseCode) {
        try {
            // Use the latest grade method to avoid duplicate issues
            return gradeService.getLatestGradeByStudentIdAndCourseCode(studentId, courseCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving grade: " + e.getMessage());
        }
    }
}