package com.example.edt_k.repository;

import com.example.edt_k.entity.SchedulePattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulePatternRepository extends JpaRepository<SchedulePattern, Integer> {
}
