package com.example.demo.service;

import com.example.demo.entity.Schedule;
import com.example.demo.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    // Create or update
    public Schedule saveSchedule(Schedule schedule) {
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
}
