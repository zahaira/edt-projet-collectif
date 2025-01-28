package com.example.edt_k.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor

@Entity
public class SchedulePattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalTime startTimes;
    private LocalTime endTimes;

    @OneToMany(mappedBy = "schedulePattern", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DaySchedulePattern> daySchedulePatterns;

    public SchedulePattern() {
    }

    public SchedulePattern(LocalTime startTimes, LocalTime endTimes) {
        this.startTimes = startTimes;
        this.endTimes = endTimes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartTimes(LocalTime startTimes) {
        this.startTimes = startTimes;
    }

    public void setEndTimes(LocalTime endTimes) {
        this.endTimes = endTimes;
    }

    public void setDaySchedulePatterns(List<DaySchedulePattern> daySchedulePatterns) {
        this.daySchedulePatterns = daySchedulePatterns;
    }

    public int getId() {
        return id;
    }

    public LocalTime getStartTimes() {
        return startTimes;
    }

    public LocalTime getEndTimes() {
        return endTimes;
    }

    public List<DaySchedulePattern> getDaySchedulePatterns() {
        return daySchedulePatterns;
    }
}
