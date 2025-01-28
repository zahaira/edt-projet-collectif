package com.example.edt_k.service;

import com.example.edt_k.entity.Prof;
import com.example.edt_k.entity.Salle;
import com.example.edt_k.entity.Module;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProfService {
    List<Prof> getProfs();

    Set<Prof> getProfsByExamenId(Long examenId);

    Prof random_surveillant(Optional<Module> course);
    //générer une liste de surveillant en fonction des salles et du module
    //ces arguments car un si la salle est amphi 2 surveillant sinon 1 , si le module es enseigné avec un prof , il ne peut pas etre enseigné par ce prof
    Set<Prof> random_surveillant_list(Set<Salle> salles , Optional<Module> course);

    Prof getProfByModule(Optional<Module> module);

    boolean haveCommonSurveillant(Set<Prof> surveillant1, Set<Prof> surveillant2);
    Prof saveProf(Prof prof);
    void updateProf(Prof prof);
    void deleteProf(Long id);
    Prof getProfById(Long id);
}
