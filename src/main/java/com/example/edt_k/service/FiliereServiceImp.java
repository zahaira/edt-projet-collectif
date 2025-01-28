package com.example.edt_k.service;

import com.example.edt_k.entity.Filiere;
import com.example.edt_k.entity.Module;
import com.example.edt_k.repository.FiliereRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FiliereServiceImp implements FiliereService{
    private FiliereRepository filiereRepository;
    @Override
    public Filiere getFiliereByModule(Optional<Module> course) {
        return filiereRepository.findByModules(course);
    }

    @Override
    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    @Override
    public Optional<Filiere> getFiliereById(Long id) {
        return filiereRepository.findById(id);
    }

    @Override
    public Filiere saveFiliere(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    @Override
    public void deleteFiliere(Long id) {
        filiereRepository.deleteById(id);
    }

    @Override
    public void updateFiliere(Long id, Filiere updatedFiliere) {
        Optional<Filiere> existingFiliere = filiereRepository.findById(id);

        if (existingFiliere.isPresent()) {
            Filiere filiereToUpdate = existingFiliere.get();
            filiereToUpdate.setNom_filiere(updatedFiliere.getNom_filiere());
            filiereToUpdate.setEffectif(updatedFiliere.getEffectif());
            // Mettez à jour d'autres propriétés si nécessaire
            filiereRepository.save(filiereToUpdate);
        } else {
            // Gérer le cas où la filière n'existe pas
            throw new RuntimeException("Filiere not found with id: " + id);
        }
    }

    @Override
    public int NombreFilire() {
        return filiereRepository.getNbreFiliere();
    }


}
