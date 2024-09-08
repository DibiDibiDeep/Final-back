package com.example.finalproj.Alim.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Alim")
public class Alim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alim_id")
    private Integer alimId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "baby_id", nullable = false)
    private Integer babyId;

    @Column(name = "content")
    private String content;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public Alim(Integer alimId, Integer userId, Integer babyId, String content, LocalDateTime date) {
        this.alimId = alimId;
        this.userId = userId;
        this.babyId = babyId;
        this.content = content;
        this.date = date;
    }

    public Alim() {

    }

    public Integer getAlimId() {
        return alimId;
    }

    public void setAlimId(Integer alimId) {
        this.alimId = alimId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBabyId() {
        return babyId;
    }

    public void setBabyId(Integer babyId) {
        this.babyId = babyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Alim{" +
                "alimId=" + alimId +
                ", userId=" + userId +
                ", babyId=" + babyId +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}