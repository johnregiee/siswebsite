package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Curriculum;
import com.example.demo.entity.CurriculumSubject;
import com.example.demo.entity.CurriculumSubjectWithFacultyDto;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.Subject;
import com.example.demo.repository.CurriculumRepository;
import com.example.demo.repository.CurriculumSubjectRepository;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.SubjectRepository;

@Service
public class CurriculumSubjectService {

    @Autowired
    private CurriculumSubjectRepository curriculumSubjectRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    // Create / Assign subject to curriculum
    public CurriculumSubject addSubjectToCurriculum(CurriculumSubject curriculumSubject) {
        return curriculumSubjectRepository.save(curriculumSubject);
    }

    // Also provide a generic 'save' method (for controller clarity)
    public CurriculumSubject save(CurriculumSubject curriculumSubject, Long facultyId) {
        // Hydrate subject
        if (curriculumSubject.getSubject() != null && curriculumSubject.getSubject().getId() != null) {
            Subject subject = subjectRepository.findById(curriculumSubject.getSubject().getId()).orElse(null);
            curriculumSubject.setSubject(subject);
        }
        // Hydrate curriculum
        if (curriculumSubject.getCurriculum() != null && curriculumSubject.getCurriculum().getId() != null) {
            Curriculum curriculum = curriculumRepository.findById(curriculumSubject.getCurriculum().getId()).orElse(null);
            curriculumSubject.setCurriculum(curriculum);
        }
        CurriculumSubject saved = curriculumSubjectRepository.save(curriculumSubject);
        Long curriculumId = curriculumSubject.getCurriculum() != null ? curriculumSubject.getCurriculum().getId() : null;
        Long subjectId = curriculumSubject.getSubject() != null ? curriculumSubject.getSubject().getId() : null;
        String subjectCode = curriculumSubject.getSubject() != null ? curriculumSubject.getSubject().getSubjectCode() : null;
        String subjectName = curriculumSubject.getSubject() != null ? curriculumSubject.getSubject().getSubjectName() : null;
        String courseCode = (curriculumSubject.getCurriculum() != null && curriculumSubject.getCurriculum().getCourse() != null)
            ? curriculumSubject.getCurriculum().getCourse().getCode() : null;
        String courseName = (curriculumSubject.getCurriculum() != null && curriculumSubject.getCurriculum().getCourse() != null)
            ? curriculumSubject.getCurriculum().getCourse().getName() : null;
        if (curriculumId != null && subjectId != null && curriculumSubject.getRoomNumber() != null && curriculumSubject.getTime() != null && curriculumSubject.getDays() != null && facultyId != null) {
            Schedule scheduleToUpdate = scheduleRepository.findByCurriculumIdAndSubjectId(curriculumId, subjectId);
            if (scheduleToUpdate == null) {
                scheduleToUpdate = new Schedule();
                scheduleToUpdate.setCurriculumId(curriculumId);
                scheduleToUpdate.setSubjectId(subjectId);
            }
            scheduleToUpdate.setCourseCode(courseCode);
            scheduleToUpdate.setCourseName(courseName);
            scheduleToUpdate.setSubjectCode(subjectCode);
            scheduleToUpdate.setSubjectName(subjectName);
            scheduleToUpdate.setRoom(curriculumSubject.getRoomNumber());
            scheduleToUpdate.setTime(curriculumSubject.getTime());
            scheduleToUpdate.setDay(curriculumSubject.getDays());
            scheduleToUpdate.setFacultyId(facultyId);
            scheduleRepository.save(scheduleToUpdate);
        }
        return saved;
    }

    // Get all curriculum-subject pairs
    public List<CurriculumSubject> getAllCurriculumSubjects() {
        return curriculumSubjectRepository.findAll();
    }

    // Alias for controller compatibility
    public List<CurriculumSubject> findAll() {
        return getAllCurriculumSubjects();
    }

    // Get by ID
    public Optional<CurriculumSubject> getById(Long id) {
        return curriculumSubjectRepository.findById(id);
    }

    // Alias for controller compatibility
    public Optional<CurriculumSubject> findById(Long id) {
        return getById(id);
    }

    // Update curriculum-subject
    @Transactional
    public Optional<CurriculumSubject> update(Long id, CurriculumSubject updated, Long facultyId) {
        System.out.println("[DEBUG] Updating CurriculumSubject ID: " + id + ", Received facultyId: " + facultyId);
        return curriculumSubjectRepository.findById(id).map(existing -> {
            existing.setYearLevel(updated.getYearLevel());
            existing.setSemester(updated.getSemester());
            existing.setUnits(updated.getUnits());
            existing.setRoomNumber(updated.getRoomNumber());
            existing.setTime(updated.getTime());
            existing.setDays(updated.getDays());
            existing.setSubject(updated.getSubject());
            existing.setCurriculum(updated.getCurriculum());
            CurriculumSubject saved = curriculumSubjectRepository.save(existing);

            Long curriculumId = updated.getCurriculum() != null ? updated.getCurriculum().getId() : null;
            Long subjectId = updated.getSubject() != null ? updated.getSubject().getId() : null;
            String subjectCode = updated.getSubject() != null ? updated.getSubject().getSubjectCode() : null;
            String subjectName = updated.getSubject() != null ? updated.getSubject().getSubjectName() : null;
            String courseCode = (updated.getCurriculum() != null && updated.getCurriculum().getCourse() != null)
                ? updated.getCurriculum().getCourse().getCode() : null;
            String courseName = (updated.getCurriculum() != null && updated.getCurriculum().getCourse() != null)
                ? updated.getCurriculum().getCourse().getName() : null;
            // Also update or create the schedule with the assigned faculty
            if (curriculumId != null && subjectId != null && updated.getRoomNumber() != null && updated.getTime() != null && updated.getDays() != null && facultyId != null) {
                Schedule scheduleToUpdate = scheduleRepository.findByCurriculumIdAndSubjectId(curriculumId, subjectId);
                if (scheduleToUpdate == null) {
                    scheduleToUpdate = new Schedule();
                    scheduleToUpdate.setCurriculumId(curriculumId);
                    scheduleToUpdate.setSubjectId(subjectId);
                }
                scheduleToUpdate.setCourseCode(courseCode);
                scheduleToUpdate.setCourseName(courseName);
                scheduleToUpdate.setSubjectCode(subjectCode);
                scheduleToUpdate.setSubjectName(subjectName);
                scheduleToUpdate.setRoom(updated.getRoomNumber());
                scheduleToUpdate.setTime(updated.getTime());
                scheduleToUpdate.setDay(updated.getDays());
                scheduleToUpdate.setFacultyId(facultyId);
                scheduleRepository.save(scheduleToUpdate);
            }
            return saved;
        });
    }

    // Delete subject from curriculum
    public boolean deleteById(Long id) {
        return curriculumSubjectRepository.findById(id).map(cs -> {
            // Delete related schedules first
            if (cs.getCurriculum() != null && cs.getSubject() != null) {
                scheduleRepository.deleteByCurriculumIdAndSubjectId(cs.getCurriculum().getId(), cs.getSubject().getId());
            }
            curriculumSubjectRepository.delete(cs);
            return true;
        }).orElse(false);
    }

    public List<CurriculumSubject> findByCurriculumId(Long curriculumId) {
        return curriculumSubjectRepository.findByCurriculumId(curriculumId); // ✅ Real implementation
    }
    // Alias for controller compatibility
    public boolean delete(Long id) {
        return deleteById(id);
    }

    public List<CurriculumSubject> findByCourseId(Long courseId) {
        return curriculumSubjectRepository.findByCourseId(courseId);
    }

    public List<CurriculumSubjectWithFacultyDto> findWithFacultyByCurriculumId(Long curriculumId) {
        return curriculumSubjectRepository.findWithFacultyByCurriculumId(curriculumId);
    }
}
