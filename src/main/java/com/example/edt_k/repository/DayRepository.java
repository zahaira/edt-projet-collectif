package com.example.edt_k.repository;

import com.example.edt_k.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {
}
