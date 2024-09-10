package com.example.finalproj.Page.repository;

import com.example.finalproj.Page.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {
    List<Page> findByBookBookId(Integer bookId);
    List<Page> findByBookBookIdOrderByPageNum(Integer bookId);
    Optional<Page> findByBookBookIdAndPageNum(Integer bookId, Integer pageNum);
}