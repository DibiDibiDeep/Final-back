package com.example.finalproj.ml.BookML;

import com.example.finalproj.Book.entity.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookMLService {

    @Value("${ml.service.url}")

    private String mlServiceUrl;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher eventPublisher;

    // 생성자
    public BookMLService(RestTemplate restTemplate, ApplicationEventPublisher eventPublisher) {
        this.restTemplate = restTemplate;
        this.eventPublisher = eventPublisher;
    }

    // 책 정보를 ML 서비스로 전송
    public void sendBookToMLService(Book book) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"book_id\": %d, \"user_id\": %d, \"title\": \"%s\", \"cover_path\": \"%s\"}",
                book.getBookId(), book.getUserId(), book.getTitle(), book.getCoverPath());
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        try {
            String mlResponse = restTemplate.postForObject(mlServiceUrl + "/process_book", request, String.class);
            System.out.println("ML Service response for book: " + mlResponse);
            eventPublisher.publishEvent(new BookMLProcessingCompletedEvent(this, mlResponse, book.getBookId()));
        } catch (Exception e) {
            System.err.println("Error in ML service process for book: " + e.getMessage());
        }
    }
}
