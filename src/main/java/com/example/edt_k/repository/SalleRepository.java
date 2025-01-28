package com.example.edt_k.repository;

import com.example.edt_k.entity.Salle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SalleRepository extends CrudRepository<Salle,Long> {
    Set<Salle> findByExamens_Id(Long examenId);
}
