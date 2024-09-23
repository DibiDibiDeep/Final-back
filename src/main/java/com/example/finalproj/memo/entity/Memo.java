package com.example.finalproj.memo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Memo")
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Integer memoId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "today_id")
    private Integer todayId;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "content")
    private String content;

    // 기본 생성자
    public Memo() {
    }

    // 모든 필드를 포함한 생성자
    public Memo(Integer memoId, Integer userId, Integer todayId, Integer bookId, LocalDateTime date, String content) {
        this.memoId = memoId;
        this.userId = userId;
        this.todayId = todayId;
        this.bookId = bookId;
        this.date = date;
        this.content = content;
    }

    // Getter와 Setter 메서드
    public Integer getMemoId() {
        return memoId;
    }

    public void setMemoId(Integer memoId) {
        this.memoId = memoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTodayId() {
        return todayId;
    }

    public void setTodayId(Integer todayId) {
        this.todayId = todayId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // toString 메서드 오버라이드
    @Override
    public String toString() {
        return "Memo{" +
                "memoId=" + memoId +
                ", userId=" + userId +
                ", todayId=" + todayId +
                ", bookId=" + bookId +
                ", date=" + date +
                ", content='" + content + '\'' +
                '}';
    }
}