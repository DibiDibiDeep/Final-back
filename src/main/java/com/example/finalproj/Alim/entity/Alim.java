package com.example.finalproj.Alim.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Alim")
public class Alim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer alimId;

    @Column(name = "baby_id")
    private Integer babyId;

    private String content;

    private String createdAt;  // MM-dd format

    public Alim(Integer alimId, Integer babyId, String content, String createdAt) {
        this.alimId = alimId;
        this.babyId = babyId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Alim() {

    }

    public Integer getAlimId() {
        return alimId;
    }

    public void setAlimId(Integer alimId) {
        this.alimId = alimId;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Alim{" +
                "alimId=" + alimId +
                ", babyId=" + babyId +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}