package com.example.finalproj.calendar.repository;

import com.example.finalproj.calendar.entity.Calendar;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    List<Calendar> findByUserId(Integer userId);
    List<Calendar> findByBabyId(Integer babyId);
    @Query("SELECT c FROM Calendar c WHERE FUNCTION('DATE', c.startTime) = :date")
    List<Calendar> findByStartTimeDate(@Param("date") LocalDate date);

    @Query("SELECT c FROM Calendar c WHERE c.startTime BETWEEN :startDate AND :endDate")
    List<Calendar> findByStartTimeBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    boolean existsByUserIdAndStartTimeAndTitle(int userId, LocalDateTime startTime, String title);
}