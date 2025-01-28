package com.example.edt_k.Web;

import com.example.edt_k.entity.Salle;
import com.example.edt_k.service.SalleServiceImp;
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
@RequestMapping("/salles")
public class SalleController {
    private SalleServiceImp salleServiceImp;

    // Afficher la liste des salles
    @GetMapping
    public String listSalles(Model model) {
        model.addAttribute("salles", salleServiceImp.getSalles());
        return "list-salles";
    }

    // Afficher le formulaire d'ajout
    @GetMapping("/add")
    public String addSalleForm(Model model) {
        model.addAttribute("salle", new Salle());
        return "add-salle";
    }

    // Enregistrer une salle
    @PostMapping
    public String saveSalle(@ModelAttribute("salle") Salle salle) {
        salleServiceImp.saveSalle(salle);
        return "redirect:/salles";
    }

    // Supprimer une salle
    @GetMapping("/delete/{id}")
    public String deleteSalle(@PathVariable("id") Long id) {
        salleServiceImp.deleteSalle(id);
        return "redirect:/salles"; // Redirection après suppression
    }
}
