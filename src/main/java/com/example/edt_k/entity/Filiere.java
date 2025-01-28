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


    @Column(name = "nom_filiere", nullable = false)
    private String nom_filiere;

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
                ", nom_filiere='" + nom_filiere + '\'' +
                ", effectif=" + effectif
                ;
    }
}
