package com.example.edt_k.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Examen")
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne(optional = false,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "examTime_id", referencedColumnName = "id")
    private DaySchedulePattern examTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gene_id", referencedColumnName = "id")
    private Gene gene;

    @OneToOne
    @JoinColumn(name = "module_id", referencedColumnName = "id", nullable = false, unique = true)
    private Module module;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "examen_prof",
            joinColumns = @JoinColumn(name = "examen_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "prof_id", referencedColumnName = "id")
    )
    private Set<Prof> profs;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "examen_salle",
            joinColumns = @JoinColumn(name = "examen_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "salle_id", referencedColumnName = "id")
    )
    private Set<Salle> salles  ;
}
