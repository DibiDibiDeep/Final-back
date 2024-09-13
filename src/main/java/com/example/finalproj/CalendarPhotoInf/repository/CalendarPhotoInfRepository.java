package com.example.finalproj.CalendarPhotoInf.repository;

import com.example.finalproj.CalendarPhotoInf.entity.CalendarPhotoInf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalendarPhotoInfRepository extends JpaRepository<CalendarPhotoInf, Integer> {
    @Query("SELECT new com.example.finalproj.CalendarPhotoInf.entity.CalendarPhotoInf(cpi.calendarPhotoInfId, cpi.calendarPhotoId, cpi.inferenceResult, cpi.inferenceDate, cpi.userId, cpi.babyId) FROM CalendarPhotoInf cpi")
    List<CalendarPhotoInf> findAllCalendarPhotoInfs();
}