package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import com.example.demo.entity.CurriculumSubject;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.Faculty;
import com.example.demo.entity.Schedule;
import com.example.demo.repository.CurriculumSubjectRepository;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.service.EnrollmentService;
import com.example.demo.controller.EnrollmentWithDetailsDto;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CurriculumSubjectRepository curriculumSubjectRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentWithDetailsDto>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
        List<EnrollmentWithDetailsDto> dtos = new ArrayList<>();
        for (Enrollment e : enrollments) {
            CurriculumSubject cs = curriculumSubjectRepository.findBySubject_SubjectCode(e.getCourseCode()).stream().findFirst().orElse(null);
            String facultyName = "";
            Integer units = null;
            if (cs != null) {
                units = cs.getUnits();
                Schedule sched = scheduleRepository.findByCurriculumIdAndSubjectId(cs.getCurriculum().getId(), cs.getSubject().getId());
                if (sched != null && sched.getFacultyId() != null) {
                    Faculty faculty = facultyRepository.findById(sched.getFacultyId()).orElse(null);
                    facultyName = faculty != null ? faculty.getFullName() : "";
                }
            }
            dtos.add(new EnrollmentWithDetailsDto(
                e.getCourseCode(),
                e.getCourseName(),
                units,
                facultyName
            ));
        }
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<?> createEnrollment(@RequestBody Map<String, Object> payload) {
        Long studentId = Long.valueOf(payload.get("studentId").toString());
        Object curriculumIdObj = payload.get("curriculumId");
        if (curriculumIdObj != null) {
            Long curriculumId = Long.valueOf(curriculumIdObj.toString());
            List<CurriculumSubject> subjects = curriculumSubjectRepository.findByCurriculumId(curriculumId);
            for (CurriculumSubject cs : subjects) {
                Enrollment newEnrollment = new Enrollment();
                newEnrollment.setStudentId(studentId);
                newEnrollment.setCourseCode(cs.getSubject().getSubjectCode());
                newEnrollment.setCourseName(cs.getSubject().getSubjectName());
                newEnrollment.setStatus("Enrolled");
                enrollmentService.saveEnrollment(newEnrollment);
            }
            return ResponseEntity.ok("Enrolled in all subjects for curriculum");
        }
        // fallback: single subject enrollment (legacy)
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseCode((String) payload.get("courseCode"));
        enrollment.setCourseName((String) payload.get("courseName"));
        enrollment.setStatus("Enrolled");
        return ResponseEntity.ok(enrollmentService.saveEnrollment(enrollment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        Optional<Enrollment> updated = enrollmentService.updateEnrollment(id, enrollment);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        boolean deleted = enrollmentService.softDeleteEnrollment(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/course/{courseCode}")
    public List<Enrollment> getEnrollmentsForCourse(@PathVariable String courseCode) {
        return enrollmentService.getEnrollmentsByCourse(courseCode);
    }
}
