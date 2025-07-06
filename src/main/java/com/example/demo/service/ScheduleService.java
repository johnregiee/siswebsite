package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Schedule;
import com.example.demo.entity.Subject;
import com.example.demo.repository.CurriculumSubjectRepository;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.SubjectRepository;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CurriculumSubjectRepository curriculumSubjectRepository;

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

    public void updateFacultyAssignmentsBySubjectName() {
        // Mapping: subject name -> facultyId
        java.util.Map<String, Long> subjectToFaculty = new java.util.HashMap<>();
        subjectToFaculty.put("Accounting", 18L); // Patricia Marie Dayao
        subjectToFaculty.put("Introduction to Computing", 15L); // Lei Dizon
        subjectToFaculty.put("Microfinance", 19L); // Noeline Talento
        subjectToFaculty.put("Object Oriented Programming", 16L); // Amari Penaranda
        subjectToFaculty.put("Integrative Programming", 16L); // Amari Penaranda
        subjectToFaculty.put("Computer Programming", 17L); // Wiss Montoya
        subjectToFaculty.put("Filipinolohiya", 17L); // Wiss Montoya
        subjectToFaculty.put("Mathematics in the Modern World", 16L); // Amari Penaranda
        subjectToFaculty.put("National Service Training Program", 15L); // Lei Dizon
        subjectToFaculty.put("Physical Activity", 17L); // Wiss Montoya
        subjectToFaculty.put("Principles of Accounting", 15L); // Lei Dizon
        subjectToFaculty.put("Purposive Communication", 16L); // Amari Penaranda

        java.util.List<Schedule> schedules = scheduleRepository.findAll();
        for (Schedule schedule : schedules) {
            String subjectName = schedule.getSubjectName();
            if (subjectName != null && subjectToFaculty.containsKey(subjectName)) {
                schedule.setFacultyId(subjectToFaculty.get(subjectName));
                scheduleRepository.save(schedule);
            }
        }
    }

}
