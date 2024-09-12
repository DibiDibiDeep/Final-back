package com.example.finalproj.memo.repository;

import com.example.finalproj.memo.entity.Memo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Integer> {

    @Query("SELECT m FROM Memo m WHERE DATE(m.date) = :date")
    List<Memo> findByDate(@Param("date") LocalDate date);

    @Query("SELECT m FROM Memo m WHERE m.date BETWEEN :startDate AND :endDate")
    List<Memo> findByDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}