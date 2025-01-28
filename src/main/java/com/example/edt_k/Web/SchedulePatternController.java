package com.example.edt_k.Web;

import com.example.edt_k.entity.SchedulePattern;
import com.example.edt_k.service.SchedulePatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/schedulePatterns")
public class SchedulePatternController {
    @Autowired
    private SchedulePatternService schedulePatternService;

    // Afficher la liste des schedulePatterns
    @GetMapping
    public String listProfs(Model model) {
        model.addAttribute("schedulePatterns", schedulePatternService.getAllSchedulePatterns());
        return "list-schedulePatterns";
    }

    // Afficher le formulaire d'ajout
    @GetMapping("/add")
    public String addProfForm(Model model) {
        model.addAttribute("schedulePattern", new SchedulePattern());
        return "add-schedulePattern";
    }

    // Enregistrer un SchedulePattern
    @PostMapping
    public String saveProf(@ModelAttribute("schedulePattern") SchedulePattern schedulePattern) {
        schedulePatternService.saveSchedulePattern(schedulePattern);
        return "redirect:/daySchedulePattern";
    }

    // Supprimer une SchedulePattern
    @GetMapping("/delete/{id}")
    public String deleteProf(@PathVariable("id") int id) {
        schedulePatternService.deleteSchedulePattern(id);
        return "redirect:/daySchedulePattern"; // Redirection après suppression
    }
}
