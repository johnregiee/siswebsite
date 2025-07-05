package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Schedule;
import com.example.demo.entity.Subject;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.SubjectRepository;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    // Create or update
    public Schedule saveSchedule(Schedule schedule) {
        // Set courseCode, courseName, subjectCode, and subjectName from subject if subjectId is present
        if (schedule.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(schedule.getSubjectId()).orElse(null);
            if (subject != null) {
                schedule.setCourseCode(subject.getSubjectCode());
                schedule.setCourseName(subject.getSubjectName());
                schedule.setSubjectCode(subject.getSubjectCode());
                schedule.setSubjectName(subject.getSubjectName());
            }
        }
        return scheduleRepository.save(schedule);
    }

    // Get all schedules
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Get by ID
    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    // Get all schedules for a specific student
    public List<Schedule> getSchedulesByStudentId(Long studentId) {
        return scheduleRepository.findByStudentId(studentId);
    }

    // Delete by ID
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> getSchedulesByFacultyId(Long facultyId) {
        return scheduleRepository.findByFacultyId(facultyId);
    }

    public List<Schedule> getSchedulesByCurriculumId(Long curriculumId) {
        return scheduleRepository.findByCurriculumId(curriculumId);
    }

    public void repairScheduleCourseFields() {
        List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            if (schedule.getSubjectId() != null) {
                Subject subject = subjectRepository.findById(schedule.getSubjectId()).orElse(null);
                if (subject != null) {
                    schedule.setCourseCode(subject.getSubjectCode());
                    schedule.setCourseName(subject.getSubjectName());
                    scheduleRepository.save(schedule);
                }
            }
        }
    }

}
