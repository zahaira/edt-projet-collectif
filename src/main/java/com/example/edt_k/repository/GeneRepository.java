package com.example.edt_k.repository;

import com.example.edt_k.entity.Gene;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneRepository extends CrudRepository<Gene,Long> {
    @Query("SELECT DISTINCT gene FROM Gene gene " +
            "LEFT JOIN FETCH gene.exams examen " +
            "LEFT JOIN FETCH examen.salles salle " +
            "LEFT JOIN FETCH examen.profs prof " +
            "LEFT JOIN FETCH examen.examTime time " +
            "LEFT JOIN FETCH examen.module " +
            "WHERE gene.id = :geneId")
    Gene findGeneWithDetails(@Param("geneId") Long geneId);

    @Query(value = "SELECT * FROM gene", nativeQuery = true)
    List<Gene> findAllGenes();
    @Modifying
    @Transactional
    @Query(value = "Delete from gene",nativeQuery = true)
    void DeleteAllGenes();

    @Query(value = "SELECT * FROM Gene ORDER BY date_generation DESC LIMIT (SELECT count(*) FROM filiere)", nativeQuery = true)
    List<Gene> findLatestGenes();

}
