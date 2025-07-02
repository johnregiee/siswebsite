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

    @Query("SELECT new com.example.demo.entity.CurriculumSubjectWithFacultyDto(cs.id, c.course.code, c.course.name, s.subjectCode, s.subjectName, cs.units, cs.roomNumber, cs.time, cs.days, null) " +
           "FROM CurriculumSubject cs " +
           "JOIN cs.curriculum c " +
           "JOIN cs.subject s " +
           "WHERE cs.curriculum.id = :curriculumId")
    List<CurriculumSubjectWithFacultyDto> findWithFacultyByCurriculumId(Long curriculumId);
}
