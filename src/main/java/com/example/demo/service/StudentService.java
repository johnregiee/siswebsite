package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Login method (already used in controller)
    public Optional<Student> login(String email, String password) {
        return studentRepository.findByEmailAndPassword(email, password);
    }

    // Save new student
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Update student info
    public Optional<Student> updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id).map(student -> {
            student.setStudentNumber(studentDetails.getStudentNumber());
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setPassword(studentDetails.getPassword());
            student.setCourse(studentDetails.getCourse());
            student.setSection(studentDetails.getSection());
            return studentRepository.save(student);
        });
    }

    // Delete student by ID
    public boolean deleteStudent(Long id) {
        return studentRepository.findById(id).map(student -> {
            studentRepository.delete(student);
            return true;
        }).orElse(false);
    }

    // Find student by email
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}
