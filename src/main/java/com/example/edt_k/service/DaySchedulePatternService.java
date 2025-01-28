package com.example.edt_k.service;

import com.example.edt_k.entity.*;
import com.example.edt_k.repository.DaySchedulePatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DaySchedulePatternService implements Exam_timeService{
    @Autowired
    private DaySchedulePatternRepository daySchedulePatternRepository;


    // Méthode pour récupérer tous les daySchedulePattern
    public List<DaySchedulePattern> getAllDaySchedulePatterns() {
        return daySchedulePatternRepository.findAll();
    }

    // Méthode pour récupérer un DaySchedulePattern par ID
    public Optional<DaySchedulePattern> getDaySchedulePatternById(Long id) {
        return daySchedulePatternRepository.findById(id);
    }

    // Méthode pour créer ou mettre à jour un DaySchedulePattern
    public DaySchedulePattern save(DaySchedulePattern prof) {
        return daySchedulePatternRepository.save(prof);
    }

    // Méthode pour supprimer un DaySchedulePattern
    public void deleteDaySchedulePattern(Long id) {
        daySchedulePatternRepository.deleteById(id);
    }
    public DaySchedulePattern random_Exam_Time(Gene gene) {
        // Obtenir une liste d'IDs aléatoires
        List<Long> ListIds = random_exam_time_int();

        Random random = new Random();
        int indexAleatoire = random.nextInt(ListIds.size());
        Long idAleatoire = ListIds.get(indexAleatoire);

        //tant le exam_time est déja affécté a un exam redonnez un indice

        DaySchedulePattern tmp = daySchedulePatternRepository.findById(idAleatoire).orElse(null);
        if(tmp != null)
        {
            boolean test=isSameExamTime(tmp,gene);
            while (test){
                indexAleatoire = random.nextInt(ListIds.size());
                idAleatoire = ListIds.get(indexAleatoire);
                tmp = daySchedulePatternRepository.findById(idAleatoire).orElse(null);
            }

        }
        //si n'est pas déja affecté

        return  tmp;
    }


    //retourne tous les ids
    public List<Long> random_exam_time_int() {
        // Récupérer le nombre total de lignes
        Long totalRows = daySchedulePatternRepository.countTotalRows();

        // Utiliser ce nombre comme limite pour la requête aléatoire
        return daySchedulePatternRepository.findRandomIds((Long) totalRows);
    }

    @Override
    public void DeleteAllExamTimes() {
    }

    @Override
    public DaySchedulePattern findExam_timeById(Long id) {
        return null;
    }
    /*@Override
    public Exam_Time random_Exam_Time(Gene gene) {
        Duration duration = durationServiceImp.random_Duration();
        Days days = daysServiceImp.random_Day();
        Exam_Time examTime = new Exam_Time();
        examTime.setDuration(duration);
        examTime.setDays(days);
        return examTime;
    }*/

    public boolean isSameExamTime(DaySchedulePattern examTime, Gene gene) {
        List<Examen> exams = gene.getExams();

        if (exams == null || exams.isEmpty()) {
            // Handle the case when exams are null or empty
            return false;
        }

        for (Examen examen : exams) {
            if (examen.getExamTime().getId() == examTime.getId()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<DaySchedulePattern> associateDaysWithExamTimes(List<Day> daysList, List<DaySchedulePattern> durationList) {
        return null;
    }

    @Override
    public List<DaySchedulePattern> getExam_Time() {
        return getAllDaySchedulePatterns();
    }

    public boolean areExamTimesOverlapping(DaySchedulePattern examTime1, DaySchedulePattern examTime2) {
        /*boolean sameDays = examTime1.getDays().equals(examTime2.getDays());

        boolean overlapDurations = durationServiceImp.checkOverlapDurations(examTime1.getDuration(),examTime2.getDuration());

        return sameDays && overlapDurations;*/
        return examTime1.getId() == examTime2.getId();
    }
}
