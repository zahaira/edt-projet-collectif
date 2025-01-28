package com.example.edt_k.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Prof")
public class Prof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prof prof = (Prof) o;
        return Objects.equals(id, prof.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Prof{" +

                ", nom='" + nom + '\'' +

                '}';
    }

    @Column(name = "nom", nullable = false)
    private String nom;


    @OneToMany(mappedBy = "prof", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Module> modules;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "prof_salle",
            joinColumns = @JoinColumn(name = "prof_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "salle_id", referencedColumnName = "id")
    )
    private Set<Salle> salles ;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "examen_prof",
            joinColumns = @JoinColumn(name = "prof_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "examen_id", referencedColumnName = "id")
    )
    private Set<Examen> examens;
}