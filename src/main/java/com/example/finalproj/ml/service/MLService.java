package com.example.finalproj.ml.service;

import com.example.finalproj.Book.entity.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLService {
    @Value("${ml.service.url}")
    private String mlServiceUrl;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public MLService(RestTemplate restTemplate, ApplicationEventPublisher eventPublisher) {
        this.restTemplate = restTemplate;
        this.eventPublisher = eventPublisher;
    }

    public void sendImageToMLService(String imagePath, Integer userId, Integer babyId, Integer calendarPhotoId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"image_path\": \"%s\", \"user_id\": %d, \"baby_id\": %d}",
                imagePath, userId, babyId);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        try {
            String mlResponse = restTemplate.postForObject(mlServiceUrl + "/process_image", request, String.class);
            System.out.println("ML Service response: " + mlResponse);
            eventPublisher.publishEvent(new MLProcessingCompletedEvent(this, mlResponse, calendarPhotoId));
        } catch (Exception e) {
            System.err.println("Error in ML service process: " + e.getMessage());
        }
    }

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