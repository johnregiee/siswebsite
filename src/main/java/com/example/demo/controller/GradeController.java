// In a new file named GradeController.java
package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Grade addGrade(@RequestBody Grade grade) {
        return gradeService.saveGrade(grade);
    }
}