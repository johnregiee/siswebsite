package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Faculty;
import com.example.demo.repository.FacultyRepository;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public Faculty saveFaculty(Faculty faculty) {
        // Optionally check if email already exists
        Optional<Faculty> existing = facultyRepository.findByEmail(faculty.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Faculty with this email already exists");
        }
        return facultyRepository.save(faculty);
    }

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Optional<Faculty> getFacultyById(Long id) {
        return facultyRepository.findById(id);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}
