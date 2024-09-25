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

    // ML 서비스 URL을 application.properties 또는 application.yml에서 가져옵니다.
    @Value("${ml.service.url}")
    private String mlServiceUrl;

    private final RestTemplate restTemplate;

    // RestTemplate을 주입받는 생성자
    public BookMLService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // AlimInf 정보를 ML 서비스로 전송하는 메소드
    public String sendAlimInfToMLService(AlimInf alimInf) {
        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", alimInf.getName());
        requestBody.put("age", alimInf.getAge());
        requestBody.put("gender", alimInf.getGender());
        requestBody.put("emotion", alimInf.getEmotion());
        requestBody.put("health", alimInf.getHealth());
        requestBody.put("nutrition", alimInf.getNutrition());
        requestBody.put("activities", alimInf.getActivitiesList());
        requestBody.put("social", alimInf.getSocial());
        requestBody.put("special", alimInf.getSpecial());
        requestBody.put("keywords", alimInf.getKeywordsList());
        requestBody.put("diary", alimInf.getDiary());
        requestBody.put("user_id", alimInf.getUserId());
        requestBody.put("baby_id", alimInf.getBabyId());

        // HTTP 요청 엔티티 생성
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // ML 서비스로 POST 요청 전송 및 응답 반환
        return restTemplate.postForObject(mlServiceUrl + "/generate_fairytale", request, String.class);
    }
}