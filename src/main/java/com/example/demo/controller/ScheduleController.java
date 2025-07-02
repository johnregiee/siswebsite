package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Course;
import com.example.demo.entity.Curriculum;
import com.example.demo.entity.CurriculumSubject;
import com.example.demo.entity.Faculty;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.Subject;
import com.example.demo.repository.CurriculumRepository;
import com.example.demo.repository.CurriculumSubjectRepository;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.service.ScheduleService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private CurriculumSubjectRepository curriculumSubjectRepository;
    @Autowired
    private CurriculumRepository curriculumRepository;

    // Get all schedules
    @GetMapping
    public List<ScheduleWithFacultyDto> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream().map(sch -> {
            String facultyName = null;
            if (sch.getFacultyId() != null) {
                facultyName = facultyRepository.findById(sch.getFacultyId())
                    .map(Faculty::getFullName).orElse(null);
            }
            return new ScheduleWithFacultyDto(sch, facultyName);
        }).toList();
    }

    // Get schedule by ID
    @GetMapping("/{id}")
    public Optional<Schedule> getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    // Get schedules by student ID
    @GetMapping("/student/{studentId}")
    public List<ScheduleWithFacultyDto> getSchedulesByStudentId(@PathVariable Long studentId) {
        List<Schedule> schedules = scheduleService.getSchedulesByStudentId(studentId);
        return schedules.stream().map(sch -> {
            String facultyName = null;
            if (sch.getFacultyId() != null) {
                facultyName = facultyRepository.findById(sch.getFacultyId())
                    .map(Faculty::getFullName).orElse(null);
            }
            return new ScheduleWithFacultyDto(sch, facultyName);
        }).toList();
    }

    // Add or update schedule
    @PostMapping
    public Schedule addSchedule(@RequestBody Schedule schedule) {
        return scheduleService.saveSchedule(schedule);
    }

    // Delete schedule
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        Schedule existing = scheduleService.getScheduleById(id).orElseThrow();
        // Update only allowed fields
        existing.setCourseCode(schedule.getCourseCode());
        existing.setCourseName(schedule.getCourseName());
        existing.setDay(schedule.getDay());
        existing.setTime(schedule.getTime());
        existing.setRoom(schedule.getRoom());
        existing.setFacultyId(schedule.getFacultyId());
        existing.setCurriculumId(schedule.getCurriculumId());
        existing.setSubjectId(schedule.getSubjectId());
        existing.setSubjectCode(schedule.getSubjectCode());
        existing.setSubjectName(schedule.getSubjectName());
        // Add more fields here if needed
        return scheduleService.saveSchedule(existing);
    }

    @GetMapping("/faculty/{facultyId}")
    public List<Schedule> getSchedulesForFaculty(@PathVariable Long facultyId) {
        return scheduleService.getSchedulesByFacultyId(facultyId);
    }

    @PostMapping("/auto-populate/{curriculumId}")
    public List<Schedule> autoPopulateSchedules(@PathVariable Long curriculumId) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId).orElseThrow();
        Course course = curriculum.getCourse();
        List<CurriculumSubject> csList = curriculumSubjectRepository.findByCurriculumId(curriculumId);
        List<Schedule> created = csList.stream().map(cs -> {
            Subject subj = cs.getSubject();
            Schedule sched = new Schedule();
            sched.setCurriculumId(curriculumId);
            sched.setSubjectId(subj.getId());
            sched.setSubjectCode(subj.getSubjectCode());
            sched.setSubjectName(subj.getSubjectName());
            sched.setCourseCode(course != null ? course.getCode() : null);
            sched.setCourseName(course != null ? course.getName() : null);
            sched.setDay(cs.getDays());
            sched.setTime(cs.getTime());
            sched.setRoom(cs.getRoomNumber());
            sched.setFacultyId(null); // or set from cs if available
            return scheduleService.saveSchedule(sched);
        }).toList();
        return created;
    }

    @GetMapping("/curriculum/{curriculumId}")
    public List<ScheduleWithFacultyDto> getSchedulesByCurriculumId(@PathVariable Long curriculumId) {
        List<Schedule> schedules = scheduleService.getSchedulesByCurriculumId(curriculumId);
        return schedules.stream().map(sch -> {
            String facultyName = null;
            if (sch.getFacultyId() != null) {
                facultyName = facultyRepository.findById(sch.getFacultyId())
                    .map(Faculty::getFullName).orElse(null);
            }
            return new ScheduleWithFacultyDto(sch, facultyName);
        }).toList();
    }

    @GetMapping("/repair-course-fields")
    public String repairCourseFields() {
        scheduleService.repairScheduleCourseFields();
        return "Done";
    }

    public static class ScheduleWithFacultyDto {
        public Long id;
        public Long studentId;
        public String courseCode;
        public String courseName;
        public String day;
        public String time;
        public String room;
        public Long facultyId;
        public String facultyName;
        public ScheduleWithFacultyDto(Schedule sch, String facultyName) {
            this.id = sch.getId();
            this.studentId = sch.getStudentId();
            this.courseCode = sch.getCourseCode();
            this.courseName = sch.getCourseName();
            this.day = sch.getDay();
            this.time = sch.getTime();
            this.room = sch.getRoom();
            this.facultyId = sch.getFacultyId();
            this.facultyName = facultyName;
        }
    }
}
