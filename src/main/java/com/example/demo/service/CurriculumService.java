package com.example.demo.service;

import com.example.demo.entity.Curriculum;
import com.example.demo.repository.CurriculumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;

    // Create new curriculum
    public Curriculum createCurriculum(Curriculum curriculum) {
        // Prevent duplicate curriculum names
        if (curriculumRepository.findByName(curriculum.getName()).isPresent()) {
            throw new RuntimeException("A curriculum with this name already exists.");
        }
        return curriculumRepository.save(curriculum);
    }

    // Get all curriculums
    public List<Curriculum> getAllCurriculums() {
        return curriculumRepository.findAll();
    }

    // Get curriculum by ID
    public Optional<Curriculum> getCurriculumById(Long id) {
        return curriculumRepository.findById(id);
    }

    // Update curriculum
    public Optional<Curriculum> updateCurriculum(Long id, Curriculum updated) {
        return curriculumRepository.findById(id).map(curriculum -> {
            curriculum.setName(updated.getName());
            curriculum.setDescription(updated.getDescription());
            return curriculumRepository.save(curriculum);
        });
    }

    // Delete curriculum
    public boolean deleteCurriculum(Long id) {
        return curriculumRepository.findById(id).map(curriculum -> {
            curriculumRepository.delete(curriculum);
            return true;
        }).orElse(false);
    }

    public List<Curriculum> getCurriculumsByCourseId(Long courseId) {
        return curriculumRepository.findByCourse_Id(courseId);
    }
}
