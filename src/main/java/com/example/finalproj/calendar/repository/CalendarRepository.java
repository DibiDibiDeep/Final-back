package com.example.finalproj.calendar.repository;

import com.example.finalproj.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    List<Calendar> findByUserId(Integer userId);
    List<Calendar> findByBabyId(Integer babyId);
    List<Calendar> findByStartTime(LocalDateTime startTime);
}