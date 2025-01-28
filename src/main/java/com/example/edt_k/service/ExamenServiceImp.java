package com.example.edt_k.service;

import com.example.edt_k.entity.Module;
import com.example.edt_k.entity.*;
import com.example.edt_k.exception.EntityNotFoundException;
import com.example.edt_k.repository.ExamenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ExamenServiceImp implements ExamenService {
    @Autowired
    private ExamenRepository examenRepository;
    @Autowired
    private SalleServiceImp salleServiceImp;
    @Autowired
    private ProfServiceImp profServiceImp;
    @Autowired
    private  DaySchedulePatternService examTimeServiceImp;


    @Override
    public List<Examen> getExams() {
        return (List<Examen>) examenRepository.findAll();
    }

    @Override
    public void DeleteAllExams() {
        examenRepository.deleteAllExams();
    }

    @Override
    public boolean isExamEmpty() {
        return examenRepository.countExamRows()>0;
    }

    @Override
    public List<Examen> getExamsByExamTime(DaySchedulePattern examTime) {
        List<Examen> examen =  examenRepository.findByExamTime(examTime);
         return unwrapExams(examen,examTime); // si il ya deja un examen dans ce meeting time => not empty => used
    }


    static List<Examen> unwrapExams(List<Examen> entities, DaySchedulePattern examTime) {
        if (entities.isEmpty()) { //si pas d'examen ds le meeting time => excep levé
            throw new EntityNotFoundException(examTime, Examen.class);
        }else {
            return entities;
        }
    }


    @Override
    public Examen random_Examen(Gene gene, Module module) {
        Set<Salle> salles = salleServiceImp.random_list_salle(gene.getFiliere().getEffectif(), module.isPrise());
        Examen examen = new Examen();
        examen.setProfs(profServiceImp.random_surveillant_list(salles, Optional.of(module)));
        examen.setModule(module);
        examen.setSalles(salles);
        examen.setGene(gene);
        examen.setExamTime(examTimeServiceImp.random_Exam_Time(gene));
        return examen;
    }

    @Override
    @Transactional
    public void saveExamen(Examen examen) {
        examenRepository.save(examen);
    }
}
