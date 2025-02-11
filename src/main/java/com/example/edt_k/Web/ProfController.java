package com.example.edt_k.Web;

import com.example.edt_k.entity.Examen;
import com.example.edt_k.entity.Prof;
import com.example.edt_k.service.*;
import com.example.edt_k.service.ProfServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/profs")
public class ProfController {
    @Autowired
    private ExamenServiceImp examenService;

    @Autowired
    private ProfService profService;

    // Afficher la liste des profs
    @GetMapping
    public String listProfs(Model model) {
        model.addAttribute("profs", profService.getProfs());
        return "list-profs";
    }

    // Afficher le formulaire d'ajout
    @GetMapping("/add")
    public String addProfForm(Model model) {
        model.addAttribute("prof", new Prof());
        return "add-prof";
    }

    // Enregistrer un prof
    @PostMapping
    public String saveProf(@ModelAttribute("prof") Prof prof) {
        profService.saveProf(prof);
        return "redirect:/profs";
    }

    // Supprimer une filière
    @GetMapping("/delete/{id}")
    public String deleteProf(@PathVariable("id") Long id) {
        profService.deleteProf(id);
        return "redirect:/profs"; // Redirection après suppression
    }

    @GetMapping("/{id}/emploi")
    public String emploiDuTemps(@PathVariable Long id, Model model) {
        List<Examen> examens = examenService.getProfEmploiDuTemps(id);
        model.addAttribute("examens", examens);
        return "prof-edt";
    }


}
