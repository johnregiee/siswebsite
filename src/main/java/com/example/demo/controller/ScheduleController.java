package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Schedule;
import com.example.demo.service.ScheduleService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // Get all schedules
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    // Get schedule by ID
    @GetMapping("/{id}")
    public Optional<Schedule> getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    // Get schedules by student ID
    @GetMapping("/student/{studentId}")
    public List<Schedule> getSchedulesByStudentId(@PathVariable Long studentId) {
        return scheduleService.getSchedulesByStudentId(studentId);
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

     @GetMapping("/faculty/{facultyId}")
    public List<Schedule> getSchedulesForFaculty(@PathVariable Long facultyId) {
        return scheduleService.getSchedulesByFacultyId(facultyId);
    }
}
