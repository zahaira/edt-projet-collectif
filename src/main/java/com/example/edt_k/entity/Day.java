package com.example.edt_k.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
    private List<DaySchedulePattern> daySchedulePatterns;

    public Day() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DaySchedulePattern> getDaySchedulePatterns() {
        return daySchedulePatterns;
    }
}
