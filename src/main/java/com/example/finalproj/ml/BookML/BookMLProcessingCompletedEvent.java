package com.example.finalproj.ml.BookML;

import org.springframework.context.ApplicationEvent;

public class BookMLProcessingCompletedEvent extends ApplicationEvent {
    private final String mlResponse;
    private final Integer bookId;

    // 생성자
    public BookMLProcessingCompletedEvent(Object source, String mlResponse, Integer bookId) {
        super(source);
        this.mlResponse = mlResponse;
        this.bookId = bookId;
    }

    // ML 응답 getter
    public String getMlResponse() {
        return mlResponse;
    }

    // 책 ID getter
    public Integer getBookId() {
        return bookId;
    }
}