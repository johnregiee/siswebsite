package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.FacultyLoginRequest;
import com.example.demo.entity.Faculty;
import com.example.demo.repository.FacultyRepository;

@CrossOrigin(origins = "*") // Allow requests from frontend
@RestController
@RequestMapping("/api/auth/faculty")
public class FacultyAuthController {

    @Autowired
    private FacultyRepository facultyRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody FacultyLoginRequest request) {
        Optional<Faculty> facultyOpt = facultyRepository.findByEmail(request.getEmail());
        if (facultyOpt.isPresent()) {
            Faculty faculty = facultyOpt.get();
            if (faculty.getPassword().equals(request.getPassword())) {
                return ResponseEntity.ok(faculty); // Success — return the faculty object
            } else {
                return ResponseEntity.status(401).body("Incorrect password");
            }
        }
        return ResponseEntity.status(401).body("Email not found");
    }
}
