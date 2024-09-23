package com.example.finalproj.ml.BookML;

import com.example.finalproj.Book.service.BookService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookMLEventListener {
    private final BookService bookService;

    // 생성자
    public BookMLEventListener(BookService bookService) {
        this.bookService = bookService;
    }

    // 책 ML 처리 완료 이벤트 핸들러
    @EventListener
    public void handleBookMLProcessingCompletedEvent(BookMLProcessingCompletedEvent event) {
        String mlResponse = event.getMlResponse();
        Integer bookId = event.getBookId();
        try {
            bookService.updateBookWithMLResponse(bookId, mlResponse);
            System.out.println("Book updated with ML response for book ID: " + bookId);
        } catch (Exception e) {
            System.err.println("Error updating book with ML response: " + e.getMessage());
        }
    }
}