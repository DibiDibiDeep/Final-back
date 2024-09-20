package com.example.finalproj.Book.repository;

import com.example.finalproj.Book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // ID로 책을 조회하면서 관련된 페이지도 함께 가져오는 쿼리
    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.pages WHERE b.bookId = :id")
    Optional<Book> findByIdWithPages(@Param("id") Integer id);
}