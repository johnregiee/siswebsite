package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    
    // This is a very important method for checking if a faculty member
    // with a given email already exists before creating a new one.
    Optional<Faculty> findByEmail(String email);
}