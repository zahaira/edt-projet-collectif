package com.example.edt_k.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "prise", nullable = false)
    private boolean prise;
    @Column(name = "nom", nullable = true)
    private String nom;

    @Override
    public String toString() {
        return "Module{" +
                ", prise=" + prise +
                ", nom='" + nom + '\'' +
                ", prof=" + prof +

                '}';
    }

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "filiere_id", referencedColumnName = "id")
    private Filiere filiere;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "module")
    private Examen examen;


    @ManyToOne(optional = false)
    @JoinColumn(name = "prof_id", referencedColumnName = "id")
    private Prof prof;

}
