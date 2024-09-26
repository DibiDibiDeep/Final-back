package com.example.finalproj.Book.entity;

import com.example.finalproj.Page.entity.Page;
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
    private LocalDateTime generatedDate;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Page> pages = new ArrayList<>();

    // 기본 생성자
    public Book() {
        this.generatedDate = LocalDateTime.now();
    }

    // bookId의 getter와 setter
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    // userId의 getter와 setter
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // title의 getter와 setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // coverPath의 getter와 setter
    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }


    // generatedDate의 getter와 setter
    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }

    // pages의 getter와 setter
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