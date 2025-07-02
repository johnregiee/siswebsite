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

import com.example.demo.entity.Course;
import com.example.demo.entity.Curriculum;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.Student;
import com.example.demo.repository.CurriculumRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.CourseService;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.saveCourse(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Optional<Course> updated = courseService.updateCourse(id, course);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            boolean deleted = courseService.deleteCourse(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(404).body("Course not found.");
            }
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.status(409)
                .body("Cannot delete course: it is still assigned to students, curriculums, or enrollments.");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("An unexpected error occurred.");
        }
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsByCourse(@PathVariable Long id) {
        return studentRepository.findByCourseId(id);
    }

    @GetMapping("/{id}/curriculums")
    public List<Curriculum> getCurriculumsByCourse(@PathVariable Long id) {
        return curriculumRepository.findByCourseId(id);
    }

    @GetMapping("/{id}/enrollments")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable Long id) {
        Optional<Course> courseOpt = courseService.getCourseById(id);
        if (courseOpt.isPresent()) {
            String code = courseOpt.get().getCode();
            return enrollmentRepository.findByCourseCode(code);
        }
        return List.of();
    }
}
