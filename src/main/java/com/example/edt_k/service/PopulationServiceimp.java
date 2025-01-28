package com.example.edt_k.service;

import com.example.edt_k.entity.Chromosome;
import com.example.edt_k.entity.Population;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class PopulationServiceimp implements PopulationService{
    private ChromosomeServiceImp chromosomeServiceImp;
    private FiliereServiceImp filiereServiceImp;

    @Override
    public Chromosome genetic_algo() {
        Population pop = generer_population();
        while (pop.getChromosomes().get(0).getFitness() != 1) {
            pop = evolve(pop);
            for (Chromosome chromosome:pop.getChromosomes()){
                chromosome.setFitness(chromosomeServiceImp.calcul_fitness(chromosome));
            }
            Collections.sort(pop.getChromosomes());
        }

        return pop.getChromosomes().get(0);
    }
    @Override
    public Population generer_population() {
        Population p=new Population();
        for (int i = 0; i < p.getSize(); i++) {
            Chromosome c=chromosomeServiceImp.generate_schedules();
            p.getChromosomes().add(c);
        }
        Collections.sort(p.getChromosomes());
        return p;
    }

    @Override
    public Population crossoverPopulation(Population population) {
        int size = population.getSize();
        Population new_generation = generer_population();
        //add the 10% of the old best population to the new generation
        IntStream.range(0,pop_per(size,0.1)).forEach(x->{
            new_generation.getChromosomes().set(x,population.getChromosomes().get(x));
        });
        //mariage of the 50% of the best old population
        IntStream.range(pop_per(size,0.1),size).forEach(x->{
            if (Math.random() < 0.9){
                int r = random_int(0,pop_per(size,0.5));
                int r2 = random_int(0,pop_per(size,0.5));
                Chromosome chromosome = chromosomeServiceImp.crossoverChromosome(population.getChromosomes().get(r),population.getChromosomes().get(r2));
                new_generation.getChromosomes().set(x,chromosome);
            }else {
                new_generation.getChromosomes().set(x,chromosomeServiceImp.generate_schedules(filiereServiceImp.getAllFilieres()));
            }
        });
        return new_generation;
    }

    @Override
    public int pop_per(int size, double pourcentage) {
        return (int)(pourcentage*size);
    }

    @Override
    public int random_int(int start, int end) {
        return  (int)((end-start)*Math.random()) + start;
    }

    @Override
    public Population evolve(Population population) {
        return crossoverPopulation(population);
    }
}
