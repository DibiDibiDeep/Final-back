package com.example.finalproj.Book.repository;

import com.example.finalproj.Book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE DATE(b.startDate) = :date OR DATE(b.endDate) = :date")
    List<Book> findByDate(@Param("date") LocalDate date);

    @Query("SELECT b FROM Book b WHERE b.startDate BETWEEN :startDate AND :endDate OR b.endDate BETWEEN :startDate AND :endDate")
    List<Book> findByDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}