package com.example.finalproj.CalendarPhoto.repository;

import com.example.finalproj.CalendarPhoto.entity.CalendarPhoto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarPhotoRepository extends JpaRepository<CalendarPhoto, Integer> {
    List<CalendarPhoto> findByUserId(Integer userId);
    List<CalendarPhoto> findByBabyId(Integer babyId);
    List<CalendarPhoto> findByDate(LocalDateTime date);
    List<CalendarPhoto> findByDateBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT cp FROM CalendarPhoto cp WHERE YEAR(cp.date) = :year AND MONTH(cp.date) = :month")
    List<CalendarPhoto> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}