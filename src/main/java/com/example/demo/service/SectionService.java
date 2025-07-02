package com.example.demo.service;

import com.example.demo.entity.Section;
import com.example.demo.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public Section saveSection(Section section) {
        return sectionRepository.save(section);
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public Optional<Section> getSectionById(Long id) {
        return sectionRepository.findById(id);
    }

    public Optional<Section> updateSection(Long id, Section sectionDetails) {
        return sectionRepository.findById(id).map(section -> {
            section.setName(sectionDetails.getName());
            return sectionRepository.save(section);
        });
    }

    public boolean deleteSection(Long id) {
        return sectionRepository.findById(id).map(section -> {
            sectionRepository.delete(section);
            return true;
        }).orElse(false);
    }
}