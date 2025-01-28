package com.example.edt_k.service;

import com.example.edt_k.entity.Filiere;
import com.example.edt_k.entity.Module;
import com.example.edt_k.repository.ModuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ModuleServiceImp implements ModuleService{
    private ModuleRepository moduleRepository;


    @Override
    public Optional<Module> getModuleById(Long id) {
        return moduleRepository.findById(id);
    }


    @Override
    public List<Module> getModulesByFiliere(Filiere filiere) {
        return moduleRepository.findByFiliere(filiere);
    }

    @Override
    public Module getById(Long id) {
        return moduleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    @Override
    public Module saveModule(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public Module updateModule(Long id, Module newModule) {
        Module existingModule = moduleRepository.findById(id).orElse(null);

        if (existingModule != null) {
            // Mettez à jour les propriétés du module existant avec les nouvelles valeurs
            existingModule.setNom(newModule.getNom());
            // Ajoutez d'autres propriétés à mettre à jour selon votre modèle

            // Enregistrez les modifications dans le repository
            return moduleRepository.save(existingModule);
        }

        return null;
    }

    @Override
    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }

}
