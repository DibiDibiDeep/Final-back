package com.example.finalproj.BabyPhoto.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Baby_Photo")
public class BabyPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baby_photo_id")
    private Integer babyPhotoId;

    @Column(name = "baby_id", nullable = false)
    private Integer babyId;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    // 모든 필드를 포함한 생성자
    public BabyPhoto(Integer babyPhotoId, Integer babyId, String filePath, LocalDateTime uploadDate) {
        this.babyPhotoId = babyPhotoId;
        this.babyId = babyId;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
    }

    // 기본 생성자
    public BabyPhoto() {
    }

    // Getter와 Setter 메서드
    public Integer getBabyPhotoId() {
        return babyPhotoId;
    }

    public void setBabyPhotoId(Integer babyPhotoId) {
        this.babyPhotoId = babyPhotoId;
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

    // toString 메서드 오버라이드
    @Override
    public String toString() {
        return "BabyPhoto{" +
                "babyPhotoId=" + babyPhotoId +
                ", babyId=" + babyId +
                ", filePath='" + filePath + '\'' +
                ", uploadDate=" + uploadDate +
                '}';
    }
}