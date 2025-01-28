package com.example.edt_k.service;

import com.example.edt_k.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
@Service
@AllArgsConstructor
public class ChromosomeServiceImp implements ChromosomeService {
    private GeneServiceImp geneServiceImp;
    private FiliereServiceImp filiereServiceImp;
    private SalleServiceImp salleServiceImp;
    private ProfServiceImp profServiceImp;
    private DaySchedulePatternService examTimeServiceImp;

    @Override
    public Chromosome generate_schedules() {
        return generate_schedules(filiereServiceImp.getAllFilieres());
    }

    @Override
    public Chromosome generate_schedules( List<Filiere> filieres) {
        Chromosome chromosome=new Chromosome();
        List<Gene> genes=new ArrayList<>();
        for (Filiere filiere:filieres){
            genes.add(geneServiceImp.generate_random_edt(filiere));
        }
        chromosome.setGenes(genes);
        chromosome.setFitness(calcul_fitness(chromosome));
        return chromosome;
    }

    @Override
    public Chromosome crossoverChromosome(Chromosome chromosome1, Chromosome chromosome2) {
        Chromosome chromosome = generate_schedules( filiereServiceImp.getAllFilieres());
        IntStream.range(0, chromosome.getGenes().size()).forEach(x->{
            if (Math.random() <0.45){
                chromosome.getGenes().set(x, chromosome1.getGenes().get(x));
            } else if (Math.random()<0.9) {
                chromosome.getGenes().set(x, chromosome2.getGenes().get(x));
            }else {
                chromosome.getGenes().set(x,geneServiceImp.generate_random_edt(chromosome1.getGenes().get(x).getFiliere()));
            }
        });
        return chromosome;
    }

    @Override
    public double calcul_fitness(Chromosome chromosome) {
        int nbrConflits = 0;
        nbrConflits+=conflit(chromosome);
       return ((double) 1 /(nbrConflits+1));
    }

    @Override
    public int conflit(Chromosome chromosome) {
        int nbr_conflits=0;
        for(int i=0;i<chromosome.getGenes().size()-1;i++){
            for (int m=0;m<chromosome.getGenes().get(i).getExams().size();m++)
            {
                for (int j=i+1;j<chromosome.getGenes().size();j++){
                    for (int n=0;n<chromosome.getGenes().get(j).getExams().size();n++){
                        if (chromosome.getGenes().get(i).getExams().get(m).getExamTime().getId()==(chromosome.getGenes().get(j).getExams().get(n).getExamTime().getId())){
                            if (salleServiceImp.haveCommonSalle(chromosome.getGenes().get(i).getExams().get(m).getSalles(),chromosome.getGenes().get(j).getExams().get(n).getSalles()))
                                nbr_conflits++;
                            if (profServiceImp.haveCommonSurveillant(chromosome.getGenes().get(i).getExams().get(m).getProfs(),chromosome.getGenes().get(j).getExams().get(n).getProfs()))
                                nbr_conflits++;
                        }
                    }
                }
            }
        }
        return nbr_conflits;
    }



    @Override
    public Chromosome genetic_algo( List<Filiere> filieres) {
        return null;
    }


}
