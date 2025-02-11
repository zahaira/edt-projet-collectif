package com.example.edt_k.repository;

import com.example.edt_k.entity.SchedulePattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface SchedulePatternRepository extends JpaRepository<SchedulePattern, Integer> {
    Optional<Object> findByStartTimesAndEndTimes(LocalTime startTimes, LocalTime endTimes);
}
