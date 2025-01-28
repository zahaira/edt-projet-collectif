package com.example.edt_k.service;

import com.example.edt_k.entity.DaySchedulePattern;
import com.example.edt_k.entity.Examen;
import com.example.edt_k.entity.Gene;
import com.example.edt_k.entity.Module;

import java.util.List;

public interface ExamenService {
    List<Examen> getExams();
    List<Examen> getExamsByExamTime(DaySchedulePattern examTime);
    //on ne doit pas avoir des examens avec les meme exam_time(date,horaire) pour une filiere donnée
    //vérifie si un créneau horaire ds examTime est déja utilisé ou affecté a un edt d'une filiere (gene)

    Examen random_Examen(Gene gene, Module module);
    void saveExamen(Examen examen);
    boolean isExamEmpty();
    void DeleteAllExams();

}
