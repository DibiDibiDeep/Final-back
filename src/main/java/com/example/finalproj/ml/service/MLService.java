package com.example.finalproj.ml.service;

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
            // ML 서비스로 이미지 전송
            String mlResponse = restTemplate.postForObject(mlServiceUrl + "/process_image", request, String.class);
            System.out.println("ML Service response: " + mlResponse);

            // 이벤트 발행
            eventPublisher.publishEvent(new MLProcessingCompletedEvent(this, mlResponse, calendarPhotoId));

        } catch (Exception e) {
            System.err.println("Error in ML service process: " + e.getMessage());
        }
    }
}