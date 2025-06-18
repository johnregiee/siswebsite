package com.example.demo.service;

import com.example.demo.entity.Enrollment;
import com.example.demo.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public Optional<Enrollment> updateEnrollment(Long id, Enrollment updatedEnrollment) {
        return enrollmentRepository.findById(id).map(enrollment -> {
            enrollment.setCourseCode(updatedEnrollment.getCourseCode());
            enrollment.setCourseName(updatedEnrollment.getCourseName());
            enrollment.setSemester(updatedEnrollment.getSemester());
            enrollment.setStatus(updatedEnrollment.getStatus());
            return enrollmentRepository.save(enrollment);
        });
    }

    public boolean deleteEnrollment(Long id) {
        return enrollmentRepository.findById(id).map(enrollment -> {
            enrollmentRepository.delete(enrollment);
            return true;
        }).orElse(false);
    }
}
