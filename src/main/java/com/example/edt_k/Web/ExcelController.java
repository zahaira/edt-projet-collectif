package com.example.edt_k.Web;

import com.example.edt_k.service.ExcelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class ExcelController {
    private final ExcelService excelService;

    @PostMapping("/upload-salles")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/salles";
        }
        excelService.saveSalles(file);
        return "redirect:/salles";
    }

    @PostMapping("/upload-profs")
    public String uploadProfsFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/profs";
        }
        excelService.saveProfs(file);
        return "redirect:/profs";
    }
    @PostMapping("/upload-modules")
    public String uploadModulesFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/modules";
        }
        excelService.saveModules(file);
        return "redirect:/modules";
    }
    @PostMapping("/upload-filieres")
    public String uploadFilieresFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/filieres";
        }
        excelService.saveFilieres(file);
        return "redirect:/filieres";
    }
}
