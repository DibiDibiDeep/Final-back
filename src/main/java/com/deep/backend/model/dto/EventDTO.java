package com.deep.backend.model.dto;

import java.time.LocalDateTime;

public class EventDTO {
    private int eventId;
    private Integer eventPhotoId;  // Integer로 변경 (null 가능)
    private int userId;
    private Integer fairyTaleId;  // Integer로 변경 (null 가능)
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;

    // Getters and setters
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Integer getEventPhotoId() {
        return eventPhotoId;
    }

    public void setEventPhotoId(Integer eventPhotoId) {
        this.eventPhotoId = eventPhotoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getFairyTaleId() {
        return fairyTaleId;
    }

    public void setFairyTaleId(Integer fairyTaleId) {
        this.fairyTaleId = fairyTaleId;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}