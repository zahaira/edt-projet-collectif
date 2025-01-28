package com.example.edt_k.repository;

import com.example.edt_k.entity.DaySchedulePattern;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaySchedulePatternRepository extends JpaRepository<DaySchedulePattern, Long> {

    // 1. Récupérer le nombre total de lignes
    @Query("SELECT COUNT(id) FROM DaySchedulePattern")
    Long countTotalRows();

    // 2. Récupérer les IDs de manière aléatoire en utilisant la limite obtenue
    @Query(value = "SELECT id FROM day_schedule_pattern ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Long> findRandomIds(Long limit);

    // Méthode pour supprimer un élément par ID
    void deleteById(Long id);

    // Méthode pour supprimer toutes les lignes de la table
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM day_schedule_pattern", nativeQuery = true)
    void deleteAllDaySchedulePatterns();
}
