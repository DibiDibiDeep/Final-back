package com.example.finalproj.BookInf.repository;

import com.example.finalproj.BookInf.entity.BookInf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookInfRepository extends JpaRepository<BookInf, Integer> {
    Optional<BookInf> findByBookId(Integer bookId);
}