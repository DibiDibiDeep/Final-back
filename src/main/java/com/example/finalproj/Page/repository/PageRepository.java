package com.example.finalproj.Page.repository;

import com.example.finalproj.Page.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {
    // 책 ID로 페이지를 조회하고 페이지 번호로 정렬
    List<Page> findByBookBookIdOrderByPageNum(Integer bookId);
}