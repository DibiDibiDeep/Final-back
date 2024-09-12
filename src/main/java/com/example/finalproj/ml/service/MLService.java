package com.example.finalproj.ml.service;

import org.springframework.beans.factory.annotation.Value;
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

    public MLService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendImageToMLService(String imagePath, Integer userId, Integer babyId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"image_path\": \"%s\", \"user_id\": %d, \"baby_id\": %d}",
                imagePath, userId, babyId);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(mlServiceUrl + "/process_image", request, String.class);
            System.out.println("ML Service response: " + response);
        } catch (Exception e) {
            System.err.println("Error sending image to ML service: " + e.getMessage());
        }
    }
}