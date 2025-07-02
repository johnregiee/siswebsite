package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    Optional<Curriculum> findByName(String name);
    List<Curriculum> findByCourseId(Long courseId);
}
