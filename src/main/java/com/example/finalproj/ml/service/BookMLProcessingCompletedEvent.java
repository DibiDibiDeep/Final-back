package com.example.finalproj.ml.service;

import org.springframework.context.ApplicationEvent;

public class BookMLProcessingCompletedEvent extends ApplicationEvent {
    private final String mlResponse;
    private final Integer bookId;

    public BookMLProcessingCompletedEvent(Object source, String mlResponse, Integer bookId) {
        super(source);
        this.mlResponse = mlResponse;
        this.bookId = bookId;
    }

    public String getMlResponse() {
        return mlResponse;
    }

    public Integer getBookId() {
        return bookId;
    }
}