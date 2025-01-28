package com.example.edt_k.service;

import com.example.edt_k.entity.Salle;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SalleService {
    List<Salle> getSalles();
    Set<Salle> getSallesByExamenId(Long examenId);
    //en se basant sur le module , il va retourner une salle
    //module necessitant prise => salle avec prise
    Salle random_salle (boolean avecPrise);
    //selon l'effectif de la filiere et la capacité des etudiants et le module si necessite prise je vais retourner une liste de salle
    Set<Salle> random_list_salle(int effectif_fil, boolean avecPrise);
    //compare s'ils ont les memes salles
    boolean haveCommonSalle(Set<Salle> salle1,Set<Salle> salle2);
    //cette fonction sera utilisé dans fitness , une fois il ya un conflit de salle increment le compteur de prob ds fitness
    Salle getSalle(Long id);
    Salle saveSalle(Salle salle);
    Salle updateSalle(Long id , Salle newSalle);
    void deleteSalle(Long id);


}

