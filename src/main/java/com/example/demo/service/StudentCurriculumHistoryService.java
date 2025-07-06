package com.example.demo.service;

import com.example.demo.entity.StudentCurriculumHistory;
import com.example.demo.repository.StudentCurriculumHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentCurriculumHistoryService {
    @Autowired
    private StudentCurriculumHistoryRepository historyRepository;

    public List<StudentCurriculumHistory> getHistoryForStudent(Long studentId) {
        return historyRepository.findByStudentIdOrderByStartDateAsc(studentId);
    }

    public void addCurriculumHistory(Long studentId, Long curriculumId) {
        // End previous current curriculum if exists
        List<StudentCurriculumHistory> history = getHistoryForStudent(studentId);
        history.stream().filter(h -> h.getEndDate() == null).forEach(h -> {
            h.setEndDate(LocalDate.now());
            historyRepository.save(h);
        });
        // Add new current curriculum
        StudentCurriculumHistory newHistory = new StudentCurriculumHistory(studentId, curriculumId, LocalDate.now(), null);
        historyRepository.save(newHistory);
    }
} 