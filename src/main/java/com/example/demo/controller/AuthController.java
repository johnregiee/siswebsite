package com.example.demo.controller;

import java.util.Optional; // You already have this DTO

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.LoginRequestDto;
import com.example.demo.entity.Faculty;
import com.example.demo.entity.Student;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.repository.StudentRepository;

@RestController
@RequestMapping("/api/auth") // Base path for all authentication requests
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    // Endpoint for Student Login: POST /api/auth/student/login
    @PostMapping("/student/login")
    public ResponseEntity<?> loginStudent(@RequestBody LoginRequestDto loginRequest) {
        // Find a student by the provided email
        Optional<Student> studentOptional = studentRepository.findByEmail(loginRequest.getEmail());

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            // Check if the password matches (plain text comparison)
            if (student.getPassword().equals(loginRequest.getPassword())) {
                // Success! Return the student's data.
                return ResponseEntity.ok(student);
            }
        }
        
        // If user not found or password incorrect, return "Unauthorized"
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password for student.");
    }

    // Endpoint for Faculty Login: POST /api/auth/faculty/login
    @PostMapping("/faculty/login")
    public ResponseEntity<?> loginFaculty(@RequestBody LoginRequestDto loginRequest) {
        // Find a faculty member by the provided email
        Optional<Faculty> facultyOptional = facultyRepository.findByEmail(loginRequest.getEmail());

        if (facultyOptional.isPresent()) {
            Faculty faculty = facultyOptional.get();
            // Check if the password matches (plain text comparison)
            if (faculty.getPassword().equals(loginRequest.getPassword())) {
                // Success! Return the faculty member's data.
                return ResponseEntity.ok(faculty);
            }
        }
        
        // If user not found or password incorrect, return "Unauthorized"
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password for faculty.");
    }
}