package com.example.edt_k.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Entity
public class DaySchedulePattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id", nullable = false)
    private Day day;

    @ManyToOne
    @JoinColumn(name = "schedulepattern_id", nullable = false)
    private SchedulePattern schedulePattern;

    @OneToMany(mappedBy = "examTime", cascade = CascadeType.ALL)
    private List<Examen> examen;

    public DaySchedulePattern() {
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public void setSchedulePattern(SchedulePattern schedulePattern) {
        this.schedulePattern = schedulePattern;
    }

    public Long getId() {
        return id;
    }

    public Day getDay() {
        return day;
    }

    public SchedulePattern getSchedulePattern() {
        return schedulePattern;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DaySchedulePattern examTime = (DaySchedulePattern) o;
        return this.day.equals(examTime.day) && this.schedulePattern.equals(examTime.schedulePattern);
    }

    @Override
    public String toString() {
        return "Exam_Time{" +
                "id=" + id +
                ", days=" + (day != null ? day.getId() : null) +
                ", duration=" + (schedulePattern != null ? schedulePattern.getId() : null) +
                '}';
    }

}
