package com.example.edt_k.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salle")
@Getter
@Setter
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "capacite", nullable = false)
    private int capacite;
    @Column(name = "prise", nullable = false)
    private boolean prise;
    @Column(name = "name", nullable = false)
    private String name;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "prof_salle",
            joinColumns = @JoinColumn(name = "salle_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "prof_id", referencedColumnName = "id")
    )
    private Set<Prof> profs ;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "examen_salle",
            joinColumns = @JoinColumn(name = "salle_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "examen_id", referencedColumnName = "id")
    )
    private Set<Examen> examens ;

    @Override
    public String toString() {
        return "Salle{" +
                ", capacite=" + capacite +
                ", prise=" + prise +
                ", name='" + name + '\'' +
                '}';
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salle salle = (Salle) o;
        return Objects.equals(id, salle.id);
    }


}
