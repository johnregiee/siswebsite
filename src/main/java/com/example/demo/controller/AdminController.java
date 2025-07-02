package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo.DTO.FacultyLoginRequest;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Faculty;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.FacultyRepository;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    // --- Autowire the new FacultyRepository ---
    @Autowired
    private FacultyRepository facultyRepository;

    // =========================================================
    // THIS IS THE NEW METHOD FOR ADDING A FACULTY MEMBER
    // =========================================================
    @PostMapping("/faculty") // Endpoint will be POST /api/admins/faculty
    public ResponseEntity<?> createFaculty(@RequestBody FacultyLoginRequest facultyDto) {
        // 1. Check if a faculty member with this email already exists
        if (facultyRepository.findByEmail(facultyDto.getEmail()).isPresent()) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409 Conflict status
                .body("A faculty member with this email already exists.");
        }

        // 2. Create a new Faculty entity
        Faculty newFaculty = new Faculty();
        newFaculty.setFullName(facultyDto.getFullName());
        newFaculty.setEmail(facultyDto.getEmail());
        // 3. Set the password directly (as requested)
        newFaculty.setPassword(facultyDto.getPassword());
        // 4. Save the new faculty member using its repository
        Faculty savedFaculty = facultyRepository.save(newFaculty);

        // 5. Return the saved faculty as JSON
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFaculty);
    }


    // =========================================================
    // YOUR EXISTING ADMIN MANAGEMENT METHODS
    // =========================================================
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminRepository.save(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin updatedAdmin) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setUsername(updatedAdmin.getUsername());
                    admin.setPassword(updatedAdmin.getPassword());
                    admin.setEmail(updatedAdmin.getEmail());
                    return ResponseEntity.ok(adminRepository.save(admin));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/faculty")
    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    @DeleteMapping("/faculty/{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long id) {
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
            return ResponseEntity.ok("Faculty deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Faculty not found.");
        }
    }

    @PutMapping("/faculty/{id}")
    public ResponseEntity<?> updateFaculty(@PathVariable Long id, @RequestBody Faculty facultyUpdate) {
        return facultyRepository.findById(id)
            .map(faculty -> {
                faculty.setFullName(facultyUpdate.getFullName());
                faculty.setEmail(facultyUpdate.getEmail());
                if (facultyUpdate.getPassword() != null && !facultyUpdate.getPassword().isEmpty()) {
                    faculty.setPassword(facultyUpdate.getPassword());
                }
                facultyRepository.save(faculty);
                return ResponseEntity.ok("Faculty updated successfully.");
            })
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Faculty not found."));
    }
}