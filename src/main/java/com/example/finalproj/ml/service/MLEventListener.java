package com.example.finalproj.ml.service;

import com.example.finalproj.AlimInf.entity.AlimInf;
import com.example.finalproj.AlimInf.service.AlimInfService;
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
    private final AlimInfService alimInfService;

    // 생성자
    public MLEventListener(RestTemplate restTemplate,
                           @Value("${app.url:http://localhost:8080}") String appUrl,
                           AlimInfService alimInfService) {
        this.restTemplate = restTemplate;
        this.appUrl = appUrl;
        this.alimInfService = alimInfService;
    }

    // ML 처리 완료 이벤트 핸들러 (기존 코드)
    @EventListener
    public void handleMLProcessingCompletedEvent(CalendarMLProcessingCompletedEvent event) {
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

    // Alim ML 처리 완료 이벤트 핸들러 (새로운 코드)
    @EventListener
    public void handleAlimMLProcessingCompletedEvent(AlimMLProcessingCompletedEvent event) {
        String mlResponse = event.getMlResponse();
        Integer alimId = event.getAlimId();

        try {
            AlimInf alimInf = MLService.createAlimInfFromMLResponse(mlResponse, alimId);
            alimInfService.createAlimInf(alimInf);
            System.out.println("AlimInf created and saved successfully from event");
        } catch (Exception e) {
            System.err.println("Error processing Alim ML response from event: " + e.getMessage());
        }
    }
}