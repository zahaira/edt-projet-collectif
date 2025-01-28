package com.example.edt_k.Web;
import com.example.edt_k.entity.Prof;
import com.example.edt_k.service.FiliereServiceImp;
import com.example.edt_k.service.ModuleServiceImp;
import com.example.edt_k.service.ProfServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.edt_k.entity.Module;

@Controller
@RequestMapping("/modules")
public class ModuleController {
    @Autowired
    private ModuleServiceImp moduleService;
    @Autowired
    private ProfServiceImp profServiceImp;
    @Autowired
    FiliereServiceImp filiereServiceImp;
    // Afficher la liste des modules
    @GetMapping
    public String listModules(Model model) {
        model.addAttribute("modules", moduleService.getAllModules());
        return "list-modules";
    }

    // Afficher le formulaire d'ajout
    @GetMapping("/add")
    public String addModuleForm(Model model) {
        model.addAttribute("module", new Module());
        model.addAttribute("profs", profServiceImp.getProfs());
        model.addAttribute("filieres", filiereServiceImp.getAllFilieres());
        return "add-module";
    }

    // Enregistrer un nouveau module
    @PostMapping
    public String saveModule(@ModelAttribute Module module) {
        moduleService.saveModule(module);
        return "redirect:/modules";
    }

    // Supprimer un module
    @GetMapping("/delete/{id}")
    public String deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return "redirect:/modules";
    }
}
