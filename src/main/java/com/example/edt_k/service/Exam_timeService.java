package com.example.edt_k.service;

import com.example.edt_k.entity.Day;
import com.example.edt_k.entity.DaySchedulePattern;
import com.example.edt_k.entity.Gene;

import java.util.List;

public interface Exam_timeService {

   DaySchedulePattern random_Exam_Time(Gene gene);
    boolean isSameExamTime(DaySchedulePattern examTime, Gene gene);
    List<DaySchedulePattern> associateDaysWithExamTimes(List<Day> daysList, List<DaySchedulePattern> durationList);
    List<DaySchedulePattern> getExam_Time();
    boolean areExamTimesOverlapping(DaySchedulePattern examTime1, DaySchedulePattern examTime2);

    List<Long> random_exam_time_int();
    void DeleteAllExamTimes();
 DaySchedulePattern findExam_timeById(Long id);

}
