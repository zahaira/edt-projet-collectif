package com.example.edt_k.service;

import com.example.edt_k.entity.Filiere;
import com.example.edt_k.entity.Module;
import com.example.edt_k.entity.Prof;
import com.example.edt_k.entity.Salle;
import com.example.edt_k.repository.FiliereRepository;
import com.example.edt_k.repository.ModuleRepository;
import com.example.edt_k.repository.ProfRepository;
import com.example.edt_k.repository.SalleRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExcelService {
    private final SalleRepository salleRepository;
    private final ProfRepository profRepository;
    private final ModuleRepository moduleRepository;
    private final FiliereRepository filiereRepository;




    public void saveSalles(MultipartFile file) {
        try {
            List<Salle> salles = parseExcelFile(file);
            for (Salle salle : salles) {
                if (!salleExistsByName(salle.getName())) {
                    salleRepository.save(salle);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }

    private boolean salleExistsByName(String name) {
        return salleRepository.findByName(name).isPresent();
    }

    public void saveProfs(MultipartFile file) {
        try {
            List<Prof> profs = parseExcelFileForProfs(file);
            for (Prof prof : profs) {
                if (!profExistsByName(prof.getNom())) {
                    profRepository.save(prof);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }
    private boolean profExistsByName(String name) {
        return profRepository.findByNom(name).isPresent();
    }
    public void saveFilieres(MultipartFile file) {
        try {
            List<Filiere> filieres = parseExcelFileForFilieres(file);
            for (Filiere filiere : filieres) {
                if (!filiereExistsByName(filiere.getNomFiliere())) {
                    filiereRepository.save(filiere);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }

    private boolean filiereExistsByName(String name) {
        return filiereRepository.findByNomFiliere(name).isPresent();
    }


    public void saveModules(MultipartFile file) {
        try {
            List<Module> modules = parseExcelFileForModules(file);
            for (Module module : modules) {
                if (!moduleExistsByName(module.getNom())) {
                    // Save Filiere if not already saved
                    Filiere filiere = module.getFiliere();
                    if (filiere != null) {
                        Optional<Filiere> existingFiliere = filiereRepository.findByNomFiliere(filiere.getNomFiliere());
                        if (existingFiliere.isEmpty()) {
                            filiereRepository.save(filiere);
                        } else {
                            module.setFiliere(existingFiliere.get());
                        }
                    }

                    // Save Prof if not already saved
                    Prof prof = module.getProf();
                    if (prof != null) {
                        Optional<Prof> existingProf = profRepository.findByNom(prof.getNom());
                        if (existingProf.isEmpty()) {
                            profRepository.save(prof);
                        } else {
                            module.setProf(existingProf.get());
                        }
                    }

                    // Save Module
                    moduleRepository.save(module);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }

    private boolean moduleExistsByName(String name) {
        return moduleRepository.findByNom(name).isPresent();
    }
    private List<Module> parseExcelFileForModules(MultipartFile file) throws IOException {
        List<Module> modules = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        boolean isFirstRow = true;
        for (Row row : sheet) {
            if (isFirstRow) {
                isFirstRow = false;
                continue; // Skip the first row
            }
            Module module = new Module();

            // Check and set the name
            Cell nameCell = row.getCell(0);
            if (nameCell != null && nameCell.getCellType() == CellType.STRING) {
                module.setNom(nameCell.getStringCellValue());
            }

            // Check and set the prof
            Cell profCell = row.getCell(1);
            if (profCell != null && profCell.getCellType() == CellType.STRING) {
                Prof prof = new Prof();
                prof.setNom(profCell.getStringCellValue());
                module.setProf(prof);
            }

            // Check and set the filiere
            Cell filiereCell = row.getCell(2);
            if (filiereCell != null && filiereCell.getCellType() == CellType.STRING) {
                Filiere filiere = new Filiere();
                filiere.setNomFiliere(filiereCell.getStringCellValue());
                module.setFiliere(filiere);

            }

            modules.add(module);
        }
        workbook.close();
        return modules;
    }
    private List<Filiere> parseExcelFileForFilieres(MultipartFile file) throws IOException {
        List<Filiere> filieres = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        boolean isFirstRow = true;
        for (Row row : sheet) {
            if (isFirstRow) {
                isFirstRow = false;
                continue; // Skip the first row
            }
            Filiere filiere = new Filiere();

            // Check and set the name
            Cell nameCell = row.getCell(0);
            if (nameCell != null && nameCell.getCellType() == CellType.STRING) {
                filiere.setNomFiliere(nameCell.getStringCellValue());
            }
            // Check and set the capacite
            Cell capaciteCell = row.getCell(1);
            if (capaciteCell != null && capaciteCell.getCellType() == CellType.NUMERIC) {
                filiere.setEffectif((int) capaciteCell.getNumericCellValue());
            }

            filieres.add(filiere);
        }
        workbook.close();
        return filieres;
    }

    private List<Salle> parseExcelFile(MultipartFile file) throws IOException {
        List<Salle> salles = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        boolean isFirstRow = true;
        for (Row row : sheet) {
            if (isFirstRow) {
                isFirstRow = false;
                continue; // Skip the first row
            }
            Salle salle = new Salle();

            // Check and set the name
            Cell nameCell = row.getCell(0);
            if (nameCell != null && nameCell.getCellType() == CellType.STRING) {
                salle.setName(nameCell.getStringCellValue());
            }

            // Check and set the capacite
            Cell capaciteCell = row.getCell(1);
            if (capaciteCell != null && capaciteCell.getCellType() == CellType.NUMERIC) {
                salle.setCapacite((int) capaciteCell.getNumericCellValue());
            }

            // Check and set the prise
            Cell priseCell = row.getCell(2);
            if (priseCell != null && priseCell.getCellType() == CellType.BOOLEAN) {
                salle.setPrise(priseCell.getBooleanCellValue());
            } else if (priseCell != null && priseCell.getCellType() == CellType.STRING) {
                salle.setPrise(Boolean.parseBoolean(priseCell.getStringCellValue()));
            }

            salles.add(salle);
        }
        workbook.close();
        return salles;
    }
    private List<Prof> parseExcelFileForProfs(MultipartFile file) throws IOException {
        List<Prof> profs = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        boolean isFirstRow = true;
        for (Row row : sheet) {
            if (isFirstRow) {
                isFirstRow = false;
                continue; // Skip the first row
            }
            Prof prof = new Prof();

            // Check and set the name
            Cell nameCell = row.getCell(0);
            if (nameCell != null && nameCell.getCellType() == CellType.STRING) {
                prof.setNom(nameCell.getStringCellValue());
            }

            profs.add(prof);
        }
        workbook.close();
        return profs;
    }
}
