package com.example.finalproj.TodaySum.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Today_sum")
public class TodaySum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "today_id")
    private Integer todayId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "fairy_tale_id")
    private Integer fairyTaleId;

    private String content;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "generated_date")
    private LocalDateTime generatedDate;

    @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    public TodaySum(){}

    public TodaySum(Integer todayId, Integer userId, Integer fairyTaleId, String content, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime generatedDate, LocalDateTime revisionDate) {
        this.todayId = todayId;
        this.userId = userId;
        this.fairyTaleId = fairyTaleId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.generatedDate = generatedDate;
        this.revisionDate = revisionDate;
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

    public Integer getFairyTaleId() {
        return fairyTaleId;
    }

    public void setFairyTaleId(Integer fairyTaleId) {
        this.fairyTaleId = fairyTaleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public LocalDateTime getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(LocalDateTime revisionDate) {
        this.revisionDate = revisionDate;
    }

    @Override
    public String toString() {
        return "TodaySum{" +
                "todayId=" + todayId +
                ", userId=" + userId +
                ", fairyTaleId=" + fairyTaleId +
                ", content='" + content + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", generatedDate=" + generatedDate +
                ", revisionDate=" + revisionDate +
                '}';
    }
}
