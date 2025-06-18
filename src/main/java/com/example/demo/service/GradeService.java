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
    
    // We will need this later for Admins/Faculty to add grades
    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }
}