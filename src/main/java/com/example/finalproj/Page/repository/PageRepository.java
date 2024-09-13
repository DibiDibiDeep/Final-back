package com.example.finalproj.Page.repository;

import com.example.finalproj.Page.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {
    List<Page> findByBookBookIdOrderByPageNum(Integer bookId);
}