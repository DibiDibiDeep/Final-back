package com.example.finalproj.TodaySum.repository;


import com.example.finalproj.TodaySum.entity.TodaySum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TodaySumRepository extends JpaRepository<TodaySum, Integer> {

    @Query("SELECT t FROM TodaySum t WHERE t.startDate BETWEEN :startDate AND :endDate")
    List<TodaySum> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}