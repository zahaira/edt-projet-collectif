package com.example.edt_k.service;

import com.example.edt_k.entity.*;
import com.example.edt_k.entity.Module;
import com.example.edt_k.repository.FiliereRepository;
import com.example.edt_k.repository.DayRepository;
import com.example.edt_k.repository.SchedulePatternRepository;
import com.example.edt_k.repository.ModuleRepository;
import com.example.edt_k.repository.ProfRepository;
import com.example.edt_k.repository.SalleRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private final DayRepository dayRepository;
    private final SchedulePatternRepository schedulePatternRepository;





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

    public void saveDays(MultipartFile file) {
        try {
            List<Day> days = parseExcelFileForDays(file);
            for (Day day : days) {
                if (day.getDate() == null) {
                    throw new IllegalArgumentException("Date cannot be null");
                }
                if (!dayExistsByDate(day.getDate())) {
                    dayRepository.save(day);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }

    private boolean dayExistsByDate(LocalDate date) {
        return dayRepository.findByDate(date).isPresent();
    }

    private List<Day> parseExcelFileForDays(MultipartFile file) throws IOException {
        List<Day> days = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        boolean isFirstRow = true;
        for (Row row : sheet) {
            if (isFirstRow) {
                isFirstRow = false;
                continue; // Skip the first row
            }
            Day day = new Day();

            // Check and set the date
            Cell dateCell = row.getCell(0);
            if (dateCell != null && dateCell.getCellType() == CellType.STRING) {
                day.setDate(LocalDate.parse(dateCell.getStringCellValue()));
            } else if (dateCell != null && dateCell.getCellType() == CellType.NUMERIC) {
                day.setDate(dateCell.getLocalDateTimeCellValue().toLocalDate());
            }

            days.add(day);
        }
        workbook.close();
        return days;
    }

    public void saveSchedulePatterns(MultipartFile file) {
        try {
            List<SchedulePattern> schedulePatterns = parseExcelFileForSchedulePatterns(file);
            for (SchedulePattern schedulePattern : schedulePatterns) {
                if (schedulePattern.getStartTimes() == null || schedulePattern.getEndTimes() == null) {
                    throw new IllegalArgumentException("Start time and end time cannot be null");
                }
                if (!schedulePatternExists(schedulePattern.getStartTimes(), schedulePattern.getEndTimes())) {
                    schedulePatternRepository.save(schedulePattern);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }

    private boolean schedulePatternExists(LocalTime startTimes, LocalTime endTimes) {
        return schedulePatternRepository.findByStartTimesAndEndTimes(startTimes, endTimes).isPresent();
    }

    private List<SchedulePattern> parseExcelFileForSchedulePatterns(MultipartFile file) throws IOException {
        List<SchedulePattern> schedulePatterns = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        boolean isFirstRow = true;
        for (Row row : sheet) {
            if (isFirstRow) {
                isFirstRow = false;
                continue; // Skip the first row
            }
            SchedulePattern schedulePattern = new SchedulePattern();

            // Check and set the start time
            Cell startTimeCell = row.getCell(0);
            if (startTimeCell != null && startTimeCell.getCellType() == CellType.STRING) {
                schedulePattern.setStartTimes(LocalTime.parse(startTimeCell.getStringCellValue()));
            } else if (startTimeCell != null && startTimeCell.getCellType() == CellType.NUMERIC) {
                schedulePattern.setStartTimes(startTimeCell.getLocalDateTimeCellValue().toLocalTime());
            }

            // Check and set the end time
            Cell endTimeCell = row.getCell(1);
            if (endTimeCell != null && endTimeCell.getCellType() == CellType.STRING) {
                schedulePattern.setEndTimes(LocalTime.parse(endTimeCell.getStringCellValue()));
            } else if (endTimeCell != null && endTimeCell.getCellType() == CellType.NUMERIC) {
                schedulePattern.setEndTimes(endTimeCell.getLocalDateTimeCellValue().toLocalTime());
            }

            schedulePatterns.add(schedulePattern);
        }
        workbook.close();
        return schedulePatterns;
    }
}
