package com.example.edt_k.service;

import com.example.edt_k.entity.*;
import com.example.edt_k.entity.Module;
import com.example.edt_k.exception.EntityNotFoundException;
import com.example.edt_k.repository.GeneRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class GeneServiceImp implements GeneService{
    private GeneRepository geneRepository;
    private ModuleServiceImp moduleServiceImp;
    private ExamenServiceImp examenServiceImp;



    @Override
    public void deleted() {
        geneRepository.DeleteAllGenes();
    }

    @Override
    public Gene generate_random_edt(Filiere filiere) {
        Gene gene = new Gene();
        gene.setFiliere(filiere);
        List<Examen> examenList = new ArrayList<>();

        for (Module module : moduleServiceImp.getModulesByFiliere(filiere)) {
            Examen examen = examenServiceImp.random_Examen(gene, module);

            while (hasSameExamTime(examenList, examen)) {
                examen = examenServiceImp.random_Examen(gene, module);
            }

            examenList.add(examen);
        }

        gene.setExams(examenList);
        return gene;
    }

    private boolean hasSameExamTime(List<Examen> examenList, Examen newExamen) {
        for (Examen existingExamen : examenList) {
            if (areExamsWithSameExamTime(existingExamen, newExamen)) {
                return true;
            }
        }
        return false;
    }

    private boolean areExamsWithSameExamTime(Examen examen1, Examen examen2) {
        return examen1.getExamTime().getId() == (examen2.getExamTime().getId());
    }

    @Override
    @Transactional
    public void saveGene(Gene gene) {
        Gene g=geneRepository.save(gene);
        for (Examen examen:g.getExams()){
            examenServiceImp.saveExamen(examen);

        }
    }

    @Override
    @Transactional
    public List<Gene> getGene() {
        return (List<Gene>) geneRepository.findAll();
    }



    @Override
    public Gene getGeneByid(long id) {
        Optional<Gene> geneOptional = geneRepository.findById(id);
        if (geneOptional.isPresent()) {
            return geneOptional.get();
        } else {
            throw new EntityNotFoundException(id, Gene.class); // Lancer une exception si le Gene n'est pas trouvé
        }
    }

    @Override
    public List<Gene> findLatestGenes() {
        return geneRepository.findLatestGenes();
    }

    @Override
    public Gene getGeneWithDetails(Long geneId) {
        return geneRepository.findGeneWithDetails(geneId);
    }
}
