package com.example.edt_k.service;

import com.example.edt_k.entity.Day;
import com.example.edt_k.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DayService {
    @Autowired
    private DayRepository dayRepository;

    // Méthode pour récupérer tous les day
    public List<Day> getAllDays() {
        return dayRepository.findAll();
    }

    // Méthode pour récupérer un day par ID
    public Optional<Day> getDayById(int id) {
        return dayRepository.findById(id);
    }

    // Méthode pour créer ou mettre à jour un day
    public Day saveDay(Day day) {
        return dayRepository.save(day);
    }

    // Méthode pour supprimer un prof
    public void deleteDay(int id) {
        dayRepository.deleteById(id);
    }
}
