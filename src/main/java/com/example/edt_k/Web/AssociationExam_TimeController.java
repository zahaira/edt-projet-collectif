package com.example.edt_k.Web;

import com.example.edt_k.entity.*;
import com.example.edt_k.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/daySchedulePattern")
public class AssociationExam_TimeController {
    @Autowired
    private DaySchedulePatternService daySchedulePatternService;
    @Autowired
    private DayService dayService;
    @Autowired
    private SchedulePatternService schedulePatternService;

    // Afficher la liste des days
    @GetMapping
    public String listDays(Model model) {
        model.addAttribute("days", dayService.getAllDays());
        model.addAttribute("schedules", schedulePatternService.getAllSchedulePatterns());
        return "list-daySchedulePattern";
    }
    @PostMapping
    public String associateDaysAndSchedules(
            @RequestParam("dayIds") List<Integer> dayIds,
            @RequestParam("scheduleIds") List<Integer> scheduleIds,
            Model model) {
        List<DaySchedulePattern> associations = new ArrayList<>();

        for (Integer dayId : dayIds) {
            Optional<Day> dayOpt = dayService.getDayById(dayId);

            if (dayOpt.isPresent()) {
                Day day = dayOpt.get();

                for (Integer scheduleId : scheduleIds) {
                    Optional<SchedulePattern> scheduleOpt = schedulePatternService.getSchedulePatternById(scheduleId);

                    if (scheduleOpt.isPresent()) {
                        SchedulePattern schedule = scheduleOpt.get();

                        // Créer une nouvelle association
                        DaySchedulePattern association = new DaySchedulePattern();
                        association.setDay(day);
                        association.setSchedulePattern(schedule);

                        // Sauvegarder l'association
                        daySchedulePatternService.save(association);
                        associations.add(association);
                    }
                }
            }
        }

        return "redirect:/daySchedulePattern"; // Redirection vers le formulaire
    }

}
