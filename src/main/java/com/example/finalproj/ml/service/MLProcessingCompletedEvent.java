package com.example.finalproj.ml.service;

import org.springframework.context.ApplicationEvent;

public class MLProcessingCompletedEvent extends ApplicationEvent {
    private final String mlResponse;
    private final Integer calendarPhotoId;

    public MLProcessingCompletedEvent(Object source, String mlResponse, Integer calendarPhotoId) {
        super(source);
        this.mlResponse = mlResponse;
        this.calendarPhotoId = calendarPhotoId;
    }

    public String getMlResponse() {
        return mlResponse;
    }

    public Integer getCalendarPhotoId() {
        return calendarPhotoId;
    }
}