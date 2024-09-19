package com.example.finalproj.ml.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MLEventListener {

    private final RestTemplate restTemplate;
    private final String appUrl;


    public MLEventListener(RestTemplate restTemplate, @Value("${app.url:http://localhost:8080}") String appUrl) {
        this.restTemplate = restTemplate;
        this.appUrl = appUrl;
    }

    @EventListener
    public void handleMLProcessingCompletedEvent(MLProcessingCompletedEvent event) {
        String mlResponse = event.getMlResponse();
        Integer calendarPhotoId = event.getCalendarPhotoId();

        String forwardUrl = appUrl + "/api/calendar-photo-inf/" + calendarPhotoId.toString();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(mlResponse, headers);

            ResponseEntity<String> forwardResponse = restTemplate.postForEntity(forwardUrl, request, String.class);
            System.out.println("Calendar Photo Inf Controller response: " + forwardResponse.getBody());
        } catch (Exception e) {
            System.err.println("Error forwarding ML response: " + e.getMessage());
        }
    }
}

