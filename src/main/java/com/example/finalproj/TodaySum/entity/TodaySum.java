package com.example.finalproj.TodaySum.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Today_sum")
public class TodaySum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "today_id")
    private Integer todayId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "book_id")
    private Integer bookId;

    private String content;

    private LocalDate date;

    public TodaySum() {}

    public TodaySum(Integer todayId, Integer userId, Integer bookId, String content, LocalDate date) {
        this.todayId = todayId;
        this.userId = userId;
        this.bookId = bookId;
        this.content = content;
        this.date = date;
    }

    public Integer getTodayId() {
        return todayId;
    }

    public void setTodayId(Integer todayId) {
        this.todayId = todayId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TodaySum{" +
                "todayId=" + todayId +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}