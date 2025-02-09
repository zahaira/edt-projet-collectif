package com.example.edt_k.Web;

import com.example.edt_k.entity.Examen;
import com.example.edt_k.entity.Prof;
import com.example.edt_k.entity.Salle;
import com.example.edt_k.service.ExamenServiceImp;
import com.example.edt_k.service.ProfServiceImp;
import com.example.edt_k.service.SalleServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/edtp")
public class Edt_Prof_Controller {

    private ProfServiceImp profServiceImp;
    private ExamenServiceImp examenServiceImp;
    private SalleServiceImp salleServiceImp;

    /**
     * Afficher l'emploi du temps complet d'un professeur
     * @param id L'ID du professeur
     * @param model Le modèle Thymeleaf
     * @return La vue contenant l'emploi du temps du professeur
     */
    @GetMapping("/prof/{id}")
    public String getProfEmploiDuTemps(@PathVariable long id, Model model) {
        // Récupérer le professeur par son ID
        Prof prof = profServiceImp.getProfById(id);

        // Récupérer tous les examens associés à ce professeur
        List<Examen> examens = examenServiceImp.getProfEmploiDuTemps(id);

        // Récupérer les informations sur les salles et autres détails nécessaires
        for (Examen examen : examens) {
            Set<Salle> salles = examen.getSalles();
            examen.setSalles(salles);  // Attacher les informations sur les salles
        }
        List<Prof> profs = profServiceImp.getProfs();

        // Ajouter les données au modèle Thymeleaf
        model.addAttribute("prof", prof);
        model.addAttribute("profs", profs);
        model.addAttribute("examens", examens);

        return "prof-edt"; // La vue qui affiche l'emploi du temps
    }
}
