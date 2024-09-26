package com.example.finalproj.ml.BookML;

import com.example.finalproj.AlimInf.entity.AlimInf;
import com.example.finalproj.baby.entity.Baby;
import com.example.finalproj.baby.service.BabyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookMLService {
    private static final Logger logger = LoggerFactory.getLogger(BookMLService.class);

    @Value("${ml.service.url}")
    private String mlServiceUrl;

    private final RestTemplate restTemplate;
    private final BabyService babyService;

    @Autowired
    public BookMLService(RestTemplate restTemplate, BabyService babyService) {
        this.restTemplate = restTemplate;
        this.babyService = babyService;
    }

    public String sendAlimInfToMLService(AlimInf alimInf) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();

        try {
            Baby baby = babyService.getBabyById(alimInf.getBabyId())
                    .orElseThrow(() -> new RuntimeException("Baby not found for babyId: " + alimInf.getBabyId()));

            validateAlimInfData(alimInf);

            requestBody.put("name", alimInf.getName());
            requestBody.put("age", calculateAge(baby.getBirth()));
            requestBody.put("gender", baby.getGender());
            requestBody.put("emotion", alimInf.getEmotion());
            requestBody.put("health", alimInf.getHealth());
            requestBody.put("nutrition", alimInf.getNutrition());
            requestBody.put("activities", alimInf.getActivitiesList());
            requestBody.put("social", alimInf.getSocial() != null ? alimInf.getSocial() : "");
            requestBody.put("special", alimInf.getSpecial());
            requestBody.put("keywords", alimInf.getKeywordsList());
            requestBody.put("diary", alimInf.getDiary());
            requestBody.put("user_id", alimInf.getUserId());
            requestBody.put("baby_id", alimInf.getBabyId());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            logger.info("ML 서비스로 요청 전송: {}", requestBody);

            String response = restTemplate.postForObject(mlServiceUrl + "/generate_fairytale", request, String.class);

            logger.info("ML 서비스로부터 응답 수신: {}", response);

            return response;
        } catch (HttpClientErrorException e) {
            logger.error("ML 서비스 클라이언트 에러: {}", e.getResponseBodyAsString());
            throw new RuntimeException("ML 서비스 요청 처리 중 오류 발생: " + e.getResponseBodyAsString(), e);
        } catch (HttpServerErrorException e) {
            logger.error("ML 서비스 서버 에러: {}", e.getResponseBodyAsString());
            throw new RuntimeException("ML 서비스 내부 오류 발생: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            logger.error("sendAlimInfToMLService 메소드에서 예기치 않은 오류 발생: ", e);
            throw new RuntimeException("AlimInf 데이터 처리 중 예기치 않은 오류 발생: " + e.getMessage(), e);
        }
    }

    private void validateAlimInfData(AlimInf alimInf) {
        if (alimInf.getName() == null || alimInf.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        // 다른 필수 필드에 대해서도 유사한 검사 수행
    }

    private int calculateAge(LocalDateTime birthDate) {
        return Period.between(birthDate.toLocalDate(), LocalDate.now()).getYears();
    }
}