package com.example.edt_k.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chromosome implements Comparable<Chromosome>{
    private double fitness=0;
    private  List<Gene> genes;

    @Override
    public String toString() {
        return "Chromosome{" +
                "fitness=" + fitness +
                ", genes=" + genes +
                '}';
    }

    @Override
    public int compareTo(Chromosome o) {
        return Double.compare(o.fitness,this.fitness);
    }
}
