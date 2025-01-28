package com.example.edt_k.repository;

import com.example.edt_k.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.edt_k.entity.Module;

import java.util.Optional;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere,Long> {
    Filiere findByModules(Optional<Module> course);
    @Query(value = "Select count(*) from filiere",nativeQuery = true)
    int getNbreFiliere();

}
