package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Enrollment;
import com.example.demo.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByStudentId(Long studentId);

    List<Enrollment> findByCourseCode(String courseCode);
    List<Schedule> findByFacultyId(Long facultyId);

    @Modifying
    @Transactional
    void deleteByCurriculumIdAndSubjectId(Long curriculumId, Long subjectId);

    Schedule findByCurriculumIdAndSubjectId(Long curriculumId, Long subjectId);

    List<Schedule> findByCurriculumId(Long curriculumId);

    List<Schedule> findAllByCurriculumIdAndSubjectId(Long curriculumId, Long subjectId);
}
