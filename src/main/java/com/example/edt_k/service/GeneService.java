package com.example.edt_k.service;

import com.example.edt_k.entity.Filiere;
import com.example.edt_k.entity.Gene;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GeneService {
    Gene generate_random_edt(Filiere filiere);
    void saveGene(Gene gene);
    List<Gene> getGene();
    Gene getGeneByid(long id);
    Gene getGeneWithDetails(Long geneId);
    void deleted();
    List<Gene> findLatestGenes();

}
