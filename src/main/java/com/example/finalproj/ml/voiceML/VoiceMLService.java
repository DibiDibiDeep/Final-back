package com.example.finalproj.ml.voiceML;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VoiceMLService {

    @Value("${ml.service.url}")
    private String mlServiceUrl;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public VoiceMLService(RestTemplate restTemplate, ApplicationEventPublisher eventPublisher) {
        this.restTemplate = restTemplate;
        this.eventPublisher = eventPublisher;
    }

    public void sendVoiceToMLService(String filePath, Integer userId, Integer babyId, Integer voiceMemoId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"voice_path\": \"%s\", \"user_id\": %d, \"baby_id\": %d}",
                filePath, userId, babyId);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        try {
            String mlResponse = restTemplate.postForObject(mlServiceUrl + "/whisper", request, String.class);
            System.out.println("ML Service response: " + mlResponse);
            eventPublisher.publishEvent(new VoiceMLProcessingCompletedEvent(this, mlResponse, voiceMemoId));
        } catch (Exception e) {
            System.err.println("Error in ML service process: " + e.getMessage());
        }
    }
}