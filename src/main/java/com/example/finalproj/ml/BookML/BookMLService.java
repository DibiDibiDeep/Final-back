package com.example.finalproj.ml.BookML;

import com.example.finalproj.AlimInf.entity.AlimInf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookMLService {
    @Value("${ml.service.url}")
    private String mlServiceUrl;

    private final RestTemplate restTemplate;

    public BookMLService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendAlimInfToMLService(AlimInf alimInf) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", alimInf.getUserId());
        requestBody.put("baby_id", alimInf.getBabyId());
        requestBody.put("name", alimInf.getName());
        requestBody.put("emotion", alimInf.getEmotion());
        requestBody.put("health", alimInf.getHealth());
        requestBody.put("nutrition", alimInf.getNutrition());
        requestBody.put("activities", alimInf.getActivities());
        requestBody.put("special", alimInf.getSpecial());
        requestBody.put("keywords", alimInf.getKeywords());
        requestBody.put("diary", alimInf.getDiary());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForObject(mlServiceUrl + "/generate_fairytale", request, String.class);
    }
}