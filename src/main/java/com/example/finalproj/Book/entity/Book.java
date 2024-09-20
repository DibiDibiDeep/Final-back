package com.example.finalproj.Book.entity;

import com.example.finalproj.Page.entity.Page;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    private Integer userId;
    private String title;
    private String coverPath;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime generatedDate;

    // Page 엔티티와의 일대다 관계 설정
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Page> pages = new ArrayList<>();

    // 기본 생성자
    public Book() {}

    // 모든 필드를 포함한 생성자
    public Book(Integer userId, String title, String coverPath, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime generatedDate) {
        this.userId = userId;
        this.title = title;
        this.coverPath = coverPath;
        this.startDate = startDate;
        this.endDate = endDate;
        this.generatedDate = generatedDate;
    }

    // Getter와 Setter 메서드
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    // 페이지 추가 메서드
    public void addPage(Page page) {
        pages.add(page);
        page.setBook(this);
    }

    // 페이지 제거 메서드
    public void removePage(Page page) {
        pages.remove(page);
        page.setBook(null);
    }
}