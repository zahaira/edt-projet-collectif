package com.example.edt_k.Web;

import com.example.edt_k.entity.Filiere;
import com.example.edt_k.service.FiliereServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/filieres")
public class FilireController {
    @Autowired
    private FiliereServiceImp filiereServiceImp;
    @GetMapping
    public String listFilieres(Model model) {
        model.addAttribute("filieres", filiereServiceImp.getAllFilieres());
        return "list-filieres"; // Correspond exactement au fichier list-filieres.html
    }
    @GetMapping("/test")
    public String testPage() {
        return "test"; // Correspond au fichier test.html
    }

    // Afficher le formulaire d'ajout
    @GetMapping("/add")
    public String addFiliereForm(Model model) {
        model.addAttribute("new_filiere", new Filiere()); // Crée un nouvel objet Filiere pour le formulaire
        return "add-filiere"; // Correspond au nom du fichier add_filiere.html
    }



    // Enregistrer une nouvelle filière
    @PostMapping
    public String saveFiliere(@ModelAttribute("filiere") Filiere filiere) {
        filiereServiceImp.saveFiliere(filiere);
        return "redirect:/filieres"; // Redirection vers la liste des filières
    }
}
