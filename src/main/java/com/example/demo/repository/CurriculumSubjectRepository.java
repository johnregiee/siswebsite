package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CurriculumSubject;
import com.example.demo.entity.CurriculumSubjectWithFacultyDto;

@Repository
public interface CurriculumSubjectRepository extends JpaRepository<CurriculumSubject, Long> {
    List<CurriculumSubject> findByCurriculumId(Long curriculumId);

    List<CurriculumSubject> findBySubject_SubjectCode(String subjectCode);

    // Find all curriculum subjects for a given courseId
    @Query("SELECT cs FROM CurriculumSubject cs JOIN cs.curriculum c WHERE c.course.id = :courseId")
    List<CurriculumSubject> findByCourseId(Long courseId);

    @Query("SELECT new com.example.demo.entity.CurriculumSubjectWithFacultyDto(cs.id, s.subjectCode, s.subjectName, cs.units, cs.roomNumber, cs.time, cs.days, f.fullName) " +
           "FROM CurriculumSubject cs " +
           "JOIN cs.subject s " +
           "LEFT JOIN Schedule sched ON sched.curriculumId = cs.curriculum.id AND sched.subjectId = cs.subject.id " +
           "LEFT JOIN Faculty f ON sched.facultyId = f.id " +
           "WHERE cs.curriculum.id = :curriculumId AND cs.isDeleted = false")
    List<CurriculumSubjectWithFacultyDto> findWithFacultyByCurriculumId(Long curriculumId);

    List<CurriculumSubject> findByCurriculumIdAndIsDeletedFalse(Long curriculumId);
}
