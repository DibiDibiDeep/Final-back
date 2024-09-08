package com.example.finalproj.FairyTale.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Fairy_Tale")
public class FairyTale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fairy_tale_id")
    private Integer fairyTaleId;

    @Column(name = "user_id")
    private Integer userId;

    private String content;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "generated_date")
    private LocalDateTime generatedDate;

    public FairyTale(){}

    public FairyTale(Integer fairyTaleId, Integer userId, String content, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime generatedDate) {
        this.fairyTaleId = fairyTaleId;
        this.userId = userId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.generatedDate = generatedDate;
    }

    public Integer getFairyTaleId() {
        return fairyTaleId;
    }

    public void setFairyTaleId(Integer fairyTaleId) {
        this.fairyTaleId = fairyTaleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "FairyTale{" +
                "fairyTaleId=" + fairyTaleId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", generatedDate=" + generatedDate +
                '}';
    }
}