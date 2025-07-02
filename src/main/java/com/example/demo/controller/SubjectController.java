package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.entity.Subject;
import com.example.demo.service.SubjectService;
import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "*")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/course/{courseId}")
    public List<Subject> getSubjectsByCourse(@PathVariable Long courseId) {
        return subjectService.getSubjectsByCourseId(courseId);
    }

    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody SubjectDto dto) {
        Subject subject = new Subject();
        subject.setSubjectCode(dto.getSubjectCode());
        subject.setSubjectName(dto.getSubjectName());
        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
            subject.setCourse(course);
        }
        Subject savedSubject = subjectService.saveSubject(subject);
        return ResponseEntity.ok(savedSubject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody SubjectDto dto) {
        return subjectService.getSubjectById(id).map(subject -> {
            subject.setSubjectCode(dto.getSubjectCode());
            subject.setSubjectName(dto.getSubjectName());
            if (dto.getCourseId() != null) {
                Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
                subject.setCourse(course);
            } else {
                subject.setCourse(null);
            }
            Subject updated = subjectService.saveSubject(subject);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body("Cannot delete subject: it is still referenced by other records.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

    // DTO for subject creation
    public static class SubjectDto {
        private String subjectCode;
        private String subjectName;
        private Long courseId;
        // getters and setters
        public String getSubjectCode() { return subjectCode; }
        public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
        public String getSubjectName() { return subjectName; }
        public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
    }
}
