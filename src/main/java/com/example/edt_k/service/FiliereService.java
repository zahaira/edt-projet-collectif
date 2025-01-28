package com.example.edt_k.service;

import com.example.edt_k.entity.Filiere;
import com.example.edt_k.entity.Module;

import java.util.List;
import java.util.Optional;

public interface FiliereService {
    Filiere getFiliereByModule(Optional<Module> course);
    List<Filiere> getAllFilieres();

    Optional<Filiere> getFiliereById(Long id);

    Filiere saveFiliere(Filiere filiere);

    void deleteFiliere(Long id);
    void updateFiliere(Long id, Filiere updatedFiliere);
    int NombreFilire();


}
