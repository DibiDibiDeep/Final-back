package com.example.finalproj.ml.AlimML;

import com.example.finalproj.Alim.entity.Alim;
import com.example.finalproj.AlimInf.entity.AlimInf;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class AlimMLService {
    @Value("${ml.service.url}")
    private String mlServiceUrl;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public AlimMLService(RestTemplate restTemplate, ApplicationEventPublisher eventPublisher) {
        this.restTemplate = restTemplate;
        this.eventPublisher = eventPublisher;
    }

    // Alim을 ML 서비스로 전송
    public void processAlim(Alim alim) {
        try {
            String mlResponse = sendAlimToMLService(alim);
            eventPublisher.publishEvent(new AlimMLProcessingCompletedEvent(this, mlResponse, alim.getAlimId()));
            System.out.println("Alim ML processing completed and event published");
        } catch (Exception e) {
            System.err.println("Error in ML service process for Alim: " + e.getMessage());
        }
    }

    private String sendAlimToMLService(Alim alim) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"user_id\": %d, \"baby_id\": %d, \"content\": \"%s\"}",
                alim.getUserId(), alim.getBabyId(), alim.getContent());
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForObject(mlServiceUrl + "/generate_diary", request, String.class);
    }

}