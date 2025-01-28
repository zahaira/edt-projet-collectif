package com.example.edt_k.Web;

import com.example.edt_k.entity.Day;
import com.example.edt_k.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;

@Controller
@RequestMapping("/days")
public class DayController {
    @Autowired
    private DayService dayService;

    // Afficher la liste des days
    @GetMapping
    public String listDays(Model model) {
        model.addAttribute("days", dayService.getAllDays());
        return "list-days";
    }

    // Afficher le formulaire d'ajout
    @GetMapping("/add")
    public String addDayForm(Model model) {
        model.addAttribute("day", new Day());
        return "add-day"; // Thymeleaf template
    }


    // Enregistrer un day
    @PostMapping
    public String saveDay(@ModelAttribute Day day, Model model) {
        try {
            // Valider la date si c'est une chaîne
            LocalDate parsedDate = day.getDate();
            day.setDate(parsedDate); // Mettre à jour la date valide

            dayService.saveDay(day); // Sauvegarder dans la base de données
            model.addAttribute("successMessage", "Le jour a été ajouté avec succès !");
        } catch (DateTimeException e) {
            model.addAttribute("errorMessage", "Format de date invalide. Veuillez entrer une date valide au format AAAA-MM-JJ.");
        }

        return "redirect:/daySchedulePattern";
    }
    @GetMapping("/details/{id}")
    public String getDayDetails(@PathVariable("id") int id, Model model) {
        // Récupérer le jour par son ID
        Day day = dayService.getDayById(id).orElse(null);

        if (day == null) {
            // Redirection ou gestion de l'erreur si le jour n'existe pas
            return "redirect:/daySchedulePattern";
        }

        // Ajouter le jour au modèle
        model.addAttribute("day", day);

        return "day-details"; // Nom du fichier HTML de la vue
    }
    // Supprimer un Day
    @GetMapping("/delete/{id}")
    public String deleteDay(@PathVariable("id") int id) {
        dayService.deleteDay(id);
        return "redirect:/days"; // Redirection après suppression
    }
}
