package com.example.edt_k.service;

import com.example.edt_k.entity.Chromosome;
import com.example.edt_k.entity.Population;

import java.util.Optional;

public interface PopulationService {
    Population generer_population();
    Population crossoverPopulation(Population population);
    int pop_per(int size,double pourcentage);
    public int random_int(int start, int end);
    public Population evolve(Population population);
    public Chromosome genetic_algo();
}
