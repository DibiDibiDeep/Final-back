package com.example.finalproj.BabyPhoto.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Baby_Photo")
public class BabyPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer babyPhotoId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "baby_id", nullable = false)
    private Integer babyId;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    public BabyPhoto(Integer babyPhotoId, Integer userId, Integer babyId, String filePath, LocalDateTime uploadDate) {
        this.babyPhotoId = babyPhotoId;
        this.userId = userId;
        this.babyId = babyId;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
    }

    public BabyPhoto() {
    }

    public Integer getBabyPhotoId() {
        return babyPhotoId;
    }

    public void setBabyPhotoId(Integer babyPhotoId) {
        this.babyPhotoId = babyPhotoId;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "BabyPhoto{" +
                "babyPhotoId=" + babyPhotoId +
                ", userId=" + userId +
                ", babyId=" + babyId +
                ", filePath='" + filePath + '\'' +
                ", uploadDate=" + uploadDate +
                '}';
    }
}