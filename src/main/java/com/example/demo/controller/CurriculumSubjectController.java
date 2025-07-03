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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CurriculumSubject;
import com.example.demo.entity.CurriculumSubjectWithFacultyDto;
import com.example.demo.service.CurriculumSubjectService;

@RestController
@RequestMapping("/api/curriculum-subjects")
@CrossOrigin(origins = "*")
public class CurriculumSubjectController {

    @Autowired
    private CurriculumSubjectService curriculumSubjectService;

    // Create a new CurriculumSubject
    @PostMapping
    public ResponseEntity<CurriculumSubject> addCurriculumSubject(
        @RequestBody CurriculumSubject cs,
        @RequestParam(required = false) Long facultyId
    ) {
        CurriculumSubject saved = curriculumSubjectService.save(cs, facultyId);
        return ResponseEntity.ok(saved);
    }

    // Get all CurriculumSubjects
    @GetMapping
    public ResponseEntity<List<CurriculumSubject>> getAllCurriculumSubjects() {
        return ResponseEntity.ok(curriculumSubjectService.findAll());
    }

    // Get a CurriculumSubject by ID
    @GetMapping("/{id}")
    public ResponseEntity<CurriculumSubject> getById(@PathVariable Long id) {
        Optional<CurriculumSubject> cs = curriculumSubjectService.findById(id);
        return cs.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Get all subjects by Curriculum ID
@GetMapping("/curriculum/{curriculumId}")
public ResponseEntity<List<CurriculumSubject>> getByCurriculumId(@PathVariable Long curriculumId) {
    List<CurriculumSubject> subjects = curriculumSubjectService.findByCurriculumId(curriculumId);
    return ResponseEntity.ok(subjects);
}

    // Get all subjects by Course ID (for grades table semester lookup)
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CurriculumSubject>> getByCourseId(@PathVariable Long courseId) {
        List<CurriculumSubject> subjects = curriculumSubjectService.findByCourseId(courseId);
        return ResponseEntity.ok(subjects);
    }

    // Update a CurriculumSubject
    @PutMapping("/{id}")
    public ResponseEntity<CurriculumSubject> updateCurriculumSubject(
            @PathVariable Long id,
            @RequestBody CurriculumSubject updated,
            @RequestParam(required = false) Long facultyId) {

        Optional<CurriculumSubject> cs = curriculumSubjectService.update(id, updated, facultyId);
        return cs.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a CurriculumSubject
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurriculumSubject(@PathVariable Long id) {
        try {
            boolean deleted = curriculumSubjectService.delete(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/curriculum/{curriculumId}/faculty-details")
    public ResponseEntity<List<CurriculumSubjectWithFacultyDto>> getCurriculumSubjectsWithFaculty(@PathVariable Long curriculumId) {
        List<CurriculumSubjectWithFacultyDto> subjects = curriculumSubjectService.findWithFacultyByCurriculumId(curriculumId);
        return ResponseEntity.ok(subjects);
    }
}
