package com.example.finalproj.BookInf.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_inf")
public class BookInf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookInfId;

    private Integer bookId;

    @Column(columnDefinition = "TEXT")
    private String inferenceResult;

    private LocalDateTime inferenceDate;
    private Integer userId;

    public BookInf() {}

    public BookInf(Integer bookId, String inferenceResult, LocalDateTime inferenceDate, Integer userId) {
        this.bookId = bookId;
        this.inferenceResult = inferenceResult;
        this.inferenceDate = inferenceDate;
        this.userId = userId;
    }

    public Integer getBookInfId() {
        return bookInfId;
    }

    public void setBookInfId(Integer bookInfId) {
        this.bookInfId = bookInfId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getInferenceResult() {
        return inferenceResult;
    }

    public void setInferenceResult(String inferenceResult) {
        this.inferenceResult = inferenceResult;
    }

    public LocalDateTime getInferenceDate() {
        return inferenceDate;
    }

    public void setInferenceDate(LocalDateTime inferenceDate) {
        this.inferenceDate = inferenceDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}