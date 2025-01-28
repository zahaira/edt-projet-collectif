package com.example.edt_k.repository;

import com.example.edt_k.entity.DaySchedulePattern;
import com.example.edt_k.entity.Examen;
import com.example.edt_k.entity.SchedulePattern;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen,Long> {
    //return all the exams at this exam time
    List<Examen> findByExamTime(DaySchedulePattern examTime);

    @Query(value = "SELECT COUNT(*) FROM examen", nativeQuery = true)
    long countExamRows();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM examen", nativeQuery = true)
    void deleteAllExams();

}
