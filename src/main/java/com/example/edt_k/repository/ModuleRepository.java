package com.example.edt_k.repository;

import com.example.edt_k.entity.Filiere;
import com.example.edt_k.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module,Long> {

    List<Module> findByFiliere(Filiere filiere);
}
