package com.example.edt_k.repository;

import com.example.edt_k.entity.Module;
import com.example.edt_k.entity.Prof;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProfRepository extends CrudRepository<Prof,Long> {
    Optional<Prof> findProfByModules(Optional<Module> module);
    Set<Prof> findByExamens_Id(Long examenId);

}
