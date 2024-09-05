package com.example.finalproj.CalendarPhoto.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Calendar_Photo")
public class CalendarPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer calendarPhotoId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "baby_id")
    private Integer babyId;

    private String filePath;

    private String takenAt;  // MM-dd format

    public CalendarPhoto(Integer calendarPhotoId, Integer userId, Integer babyId, String filePath, String takenAt) {
        this.calendarPhotoId = calendarPhotoId;
        this.userId = userId;
        this.babyId = babyId;
        this.filePath = filePath;
        this.takenAt = takenAt;
    }

    public CalendarPhoto() {

    }

    public Integer getCalendarPhotoId() {
        return calendarPhotoId;
    }

    public void setCalendarPhotoId(Integer calendarPhotoId) {
        this.calendarPhotoId = calendarPhotoId;
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

    public String getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(String takenAt) {
        this.takenAt = takenAt;
    }

    @Override
    public String toString() {
        return "CalendarPhoto{" +
                "calendarPhotoId=" + calendarPhotoId +
                ", userId=" + userId +
                ", babyId=" + babyId +
                ", filePath='" + filePath + '\'' +
                ", takenAt='" + takenAt + '\'' +
                '}';
    }
}