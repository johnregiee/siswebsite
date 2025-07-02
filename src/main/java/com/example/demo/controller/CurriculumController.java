package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Curriculum;
import com.example.demo.entity.CurriculumSubjectWithFacultyDto;
import com.example.demo.repository.CurriculumSubjectRepository;
import com.example.demo.service.CurriculumService;

@RestController
@RequestMapping("/api/curriculums")
@CrossOrigin(origins = "*")
public class CurriculumController {

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private CurriculumSubjectRepository curriculumSubjectRepository;

    // Create new curriculum
    @PostMapping
    public ResponseEntity<?> createCurriculum(@RequestBody Curriculum curriculum) {
        try {
            Curriculum saved = curriculumService.createCurriculum(curriculum);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    // Get all curriculums
    @GetMapping
    public ResponseEntity<List<Curriculum>> getAllCurriculums() {
        return ResponseEntity.ok(curriculumService.getAllCurriculums());
    }

    // Get curriculum by ID
    @GetMapping("/{id}")
    public ResponseEntity<Curriculum> getCurriculumById(@PathVariable Long id) {
        Optional<Curriculum> curriculum = curriculumService.getCurriculumById(id);
        return curriculum.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update curriculum
    @PutMapping("/{id}")
    public ResponseEntity<Curriculum> updateCurriculum(
            @PathVariable Long id,
            @RequestBody Curriculum curriculumDetails) {
        Optional<Curriculum> updated = curriculumService.updateCurriculum(id, curriculumDetails);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete curriculum
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurriculum(@PathVariable Long id) {
        boolean deleted = curriculumService.deleteCurriculum(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Get all subjects for a curriculum (for subject table refresh)
    @GetMapping("/{curriculumId}/subjects")
    public ResponseEntity<List<CurriculumSubjectWithFacultyDto>> getSubjectsForCurriculum(@PathVariable Long curriculumId) {
        List<CurriculumSubjectWithFacultyDto> subjects = curriculumSubjectRepository.findWithFacultyByCurriculumId(curriculumId);
        return ResponseEntity.ok(subjects);
    }

}
