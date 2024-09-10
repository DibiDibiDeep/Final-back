package com.example.finalproj.Book.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "cover_path", nullable = false)
    private String coverPath;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "generated_date", nullable = false)
    private LocalDateTime generatedDate;

    public Book() {
    }

    public Book(Integer bookId, Integer userId, String title, String coverPath, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime generatedDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.title = title;
        this.coverPath = coverPath;
        this.startDate = startDate;
        this.endDate = endDate;
        this.generatedDate = generatedDate;
    }

    // Getters and setters

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

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", coverPath='" + coverPath + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", generatedDate=" + generatedDate +
                '}';
    }
}