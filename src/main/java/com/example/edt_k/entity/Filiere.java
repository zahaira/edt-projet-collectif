package com.example.edt_k.entity;

import com.example.edt_k.entity.Gene;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "filiere")
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "nomFiliere", nullable = false)
    private String nomFiliere;

    public String getNomFiliere() {
        return nomFiliere;
    }

    public void setNomFiliere(String nomFiliere) {
        this.nomFiliere = nomFiliere;
    }

    @Column(name = "effectif", nullable = false)
    private int effectif;

    @JsonIgnore
    @OneToOne(mappedBy = "filiere", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Gene gene;

    @JsonIgnore
    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Module> modules;

    @Override
    public String toString() {
        return "Filiere{" +
                "id=" + id +
                ", nomFiliere='" + nomFiliere + '\'' +
                ", effectif=" + effectif
                ;
    }
}
