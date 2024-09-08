package com.example.finalproj.Alim.repository;

import com.example.finalproj.Alim.entity.Alim;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AlimRepository extends JpaRepository<Alim, Integer> {
    @Query("SELECT a FROM Alim a WHERE DATE(a.date) = :date")
    List<Alim> findByDate(@Param("date") LocalDate date);

    List<Alim> findByUserIdAndDateBetween(Integer userId, LocalDateTime start, LocalDateTime end);
}