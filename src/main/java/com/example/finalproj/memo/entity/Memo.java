package com.example.finalproj.memo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Memo")
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memoId;

    @Column(name = "today_id")
    private Integer todayId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "calendar_id")
    private Integer calendarId;

    private String createdAt;  // MM-dd format

    private String content;

    public Memo(Integer memoId, Integer todayId, Integer userId, Integer calendarId, String createdAt, String content) {
        this.memoId = memoId;
        this.todayId = todayId;
        this.userId = userId;
        this.calendarId = calendarId;
        this.createdAt = createdAt;
        this.content = content;
    }

    public Memo() {

    }

    public Integer getMemoId() {
        return memoId;
    }

    public void setMemoId(Integer memoId) {
        this.memoId = memoId;
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

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Memo{" +
                "memoId=" + memoId +
                ", todayId=" + todayId +
                ", userId=" + userId +
                ", calendarId=" + calendarId +
                ", createdAt='" + createdAt + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}