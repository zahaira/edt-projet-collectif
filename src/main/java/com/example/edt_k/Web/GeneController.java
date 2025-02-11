package com.example.edt_k.Web;

import com.example.edt_k.entity.*;
import com.example.edt_k.service.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@AllArgsConstructor
@Transactional
@RequestMapping("/edt")
public class GeneController {

    private PopulationServiceimp populationServiceimp;
    private GeneServiceImp geneService;
    private ExamenServiceImp examenServiceImp;
    @GetMapping("/test-genetic-algo")
    public String testGeneticAlgo(Model model) {
        // Supprimer tous les examens existants et exécuter l'algorithme génétique
        //examenServiceImp.DeleteAllExams();
        //geneService.deleted();
        Chromosome result = populationServiceimp.genetic_algo();

        // Enregistrer les nouveaux gènes dans la base de données
        List<Gene> genes = result.getGenes();
        for (Gene gene : genes) {
            gene.setCurrentDate(LocalDateTime.now());
            geneService.saveGene(gene);
        }

        // Ajouter les gènes au modèle pour les afficher dans une vue
        model.addAttribute("genes", genes);
        return "geneticResults"; // Correspond au fichier Thymeleaf geneticResults.html
    }
    @GetMapping("/generer")
    public String Generer(Model model){
        return "generate";
    }
    @GetMapping
    public String getEdt(Model model){
        List<Gene> genes = geneService.getGene();
        model.addAttribute("genes", genes);
        return "geneticResults"; // Correspond au fichier Thymeleaf geneticResults.html
    }

    @GetMapping("/classe")
    public String afficherGenes(Model model) {
        // Récupérer tous les gènes et les ajouter au modèle
        List<Gene> genes = geneService.getGene();
        model.addAttribute("genes", genes);
        return "classe"; // Correspond au fichier Thymeleaf classe.html
    }

    @GetMapping("/essai")
    public String affichageAll(Model model) {
        // Récupérer et traiter tous les gènes
        List<Gene> tmp = geneService.getGene();
        List<Gene> geneDTOS = new ArrayList<>(tmp);

        // Ajouter les gènes transformés au modèle
        model.addAttribute("genes", geneDTOS);
        return "essai"; // Correspond au fichier Thymeleaf essai.html
    }

    @GetMapping("/{id}")
    public String getGene(@PathVariable long id, Model model) {
        // Récupérer un gène spécifique et l'ajouter au modèle
        Gene geneById = geneService.getGeneWithDetails(id);
        model.addAttribute("gene", geneById);
        return "geneDetails"; // Correspond au fichier Thymeleaf geneDetails.html
    }
}
