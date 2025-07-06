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

import com.example.demo.entity.Course;
import com.example.demo.entity.Section;
import com.example.demo.entity.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.SectionRepository;
import com.example.demo.service.StudentService;
import com.example.demo.service.StudentCurriculumHistoryService;
import com.example.demo.repository.CurriculumRepository;

@RestController
@RequestMapping("/api/admin/student")
@CrossOrigin(origins = "*") // (optional) allow frontend requests
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private StudentCurriculumHistoryService studentCurriculumHistoryService;
    @Autowired
    private CurriculumRepository curriculumRepository;

    // Existing login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@RequestBody LoginRequest request) {
        Optional<Student> student = studentService.login(request.getEmail(), request.getPassword());

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // Create student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentDto dto) {
        Student student = new Student();
        student.setStudentNumber(dto.getStudentNumber());
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPassword(dto.getPassword());
        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
            student.setCourse(course);
        }
        if (dto.getSectionId() != null) {
            Section section = sectionRepository.findById(dto.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found"));
            student.setSection(section);
        }
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(savedStudent);
    }

    // Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Get student by id
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> updatedStudent = studentService.updateStudent(id, studentDetails);
        if (updatedStudent.isPresent()) {
            if (studentDetails.getSection() != null && studentDetails.getSection().getId() != null) {
                Section section = sectionRepository.findById(studentDetails.getSection().getId())
                    .orElseThrow(() -> new RuntimeException("Section not found"));
                studentDetails.setSection(section);
            }
            return ResponseEntity.ok(updatedStudent.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get current logged-in student info (TEMP: by email query param)
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentStudent(@RequestParam String email) {
        Optional<Student> student = studentService.getStudentByEmail(email);
        if (student.isPresent()) {
            Student s = student.get();
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
                put("name", s.getName());
                put("studentNumber", s.getStudentNumber());
            }});
        }
        return ResponseEntity.status(404).body("Student not found");
    }

    // Get all curriculums (current and previous) for a student
    @GetMapping("/{studentId}/curriculum-history")
    public List<Object> getCurriculumHistory(@PathVariable Long studentId) {
        var history = studentCurriculumHistoryService.getHistoryForStudent(studentId);
        List<Object> result = new java.util.ArrayList<>();
        for (var h : history) {
            var curriculumOpt = curriculumRepository.findById(h.getCurriculumId());
            curriculumOpt.ifPresent(curriculum -> {
                var map = new java.util.HashMap<String, Object>();
                map.put("id", curriculum.getId());
                map.put("name", curriculum.getName());
                map.put("startDate", h.getStartDate());
                map.put("endDate", h.getEndDate());
                result.add(map);
            });
        }
        return result;
    }

    // Inner class for login request DTO
    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // DTO for student creation
    public static class StudentDto {
        private String studentNumber;
        private String name;
        private String email;
        private String password;
        private Long courseId;
        private Long sectionId;
        // Getters and setters
        public String getStudentNumber() { return studentNumber; }
        public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
        public Long getSectionId() { return sectionId; }
        public void setSectionId(Long sectionId) { this.sectionId = sectionId; }
    }

}
