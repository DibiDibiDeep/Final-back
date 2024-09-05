package com.example.finalproj.calendar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer calendarId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "calendar_photo_id")
    private Integer calendarPhotoId;

    @Column(name = "baby_id")
    private Integer babyId;

    private String title;

    private String description;

    private String date;  // MM-dd format

    private String location;
    public Calendar() {

    }
    public Calendar(Integer calendarId, Integer userId, Integer calendarPhotoId, Integer babyId, String title, String description, String date, String location) {
        this.calendarId = calendarId;
        this.userId = userId;
        this.calendarPhotoId = calendarPhotoId;
        this.babyId = babyId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCalendarPhotoId() {
        return calendarPhotoId;
    }

    public void setCalendarPhotoId(Integer calendarPhotoId) {
        this.calendarPhotoId = calendarPhotoId;
    }

    public Integer getBabyId() {
        return babyId;
    }

    public void setBabyId(Integer babyId) {
        this.babyId = babyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "calendarId=" + calendarId +
                ", userId=" + userId +
                ", calendarPhotoId=" + calendarPhotoId +
                ", babyId=" + babyId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}