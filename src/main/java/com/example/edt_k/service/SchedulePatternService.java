package com.example.edt_k.service;

import com.example.edt_k.entity.SchedulePattern;
import com.example.edt_k.repository.SchedulePatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchedulePatternService {
    @Autowired
    private SchedulePatternRepository schedulePatternRepository;

    // Méthode pour récupérer tous les schedulePatterns
    public List<SchedulePattern> getAllSchedulePatterns() {
        return schedulePatternRepository.findAll();
    }

    // Méthode pour récupérer un schedulePattern par ID
    public Optional<SchedulePattern> getSchedulePatternById(int id) {
        return schedulePatternRepository.findById(id);
    }

    // Méthode pour créer ou mettre à jour un schedulePattern
    public SchedulePattern saveSchedulePattern(SchedulePattern schedulePattern) {
        return schedulePatternRepository.save(schedulePattern);
    }

    // Méthode pour supprimer un schedulePattern
    public void deleteSchedulePattern(int id) {
        schedulePatternRepository.deleteById(id);
    }
}
