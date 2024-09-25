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
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookMLService {
    // 로깅을 위한 Logger 인스턴스를 생성합니다.
    private static final Logger logger = LoggerFactory.getLogger(BookMLService.class);

    // application.properties 또는 application.yml에서 ML 서비스 URL을 가져옵니다.
    @Value("${ml.service.url}")
    private String mlServiceUrl;

    // HTTP 요청을 보내기 위한 RestTemplate 인스턴스입니다.
    private final RestTemplate restTemplate;

    // Baby 정보를 가져오기 위한 BabyService 인스턴스입니다.
    private final BabyService babyService;

    // 생성자를 통해 RestTemplate과 BabyService를 주입받습니다.
    @Autowired
    public BookMLService(RestTemplate restTemplate, BabyService babyService) {
        this.restTemplate = restTemplate;
        this.babyService = babyService;
    }

    // AlimInf 정보를 ML 서비스로 전송하는 메소드입니다.
    public String sendAlimInfToMLService(AlimInf alimInf) {
        // HTTP 헤더를 설정합니다.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문을 생성합니다.
        Map<String, Object> requestBody = new HashMap<>();

        try {
            // Baby 정보를 가져옵니다. 해당 Baby가 없으면 예외를 발생시킵니다.
            Baby baby = babyService.getBabyById(alimInf.getBabyId())
                    .orElseThrow(() -> new RuntimeException("Baby not found for babyId: " + alimInf.getBabyId()));

            // AlimInf에서 name 정보를 가져옵니다.
            requestBody.put("name", alimInf.getName());

            // Baby에서 age와 gender 정보를 가져옵니다.
            requestBody.put("age", calculateAge(baby.getBirth()));
            requestBody.put("gender", baby.getGender());

            // AlimInf에서 나머지 정보를 가져옵니다.
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

            // HTTP 요청 엔티티를 생성합니다.
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // 요청 내용을 로그로 기록합니다.
            logger.info("ML 서비스로 요청 전송: {}", requestBody);

            // ML 서비스로 POST 요청을 전송하고 응답을 받습니다.
            String response = restTemplate.postForObject(mlServiceUrl + "/generate_fairytale", request, String.class);

            // 응답 내용을 로그로 기록합니다.
            logger.info("ML 서비스로부터 응답 수신: {}", response);

            return response;
        } catch (Exception e) {
            // 오류 발생 시 로그를 기록하고 예외를 다시 던집니다.
            logger.error("sendAlimInfToMLService 메소드에서 오류 발생: ", e);
            throw new RuntimeException("AlimInf 데이터 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }

    // 주어진 생년월일을 기준으로 현재 나이를 계산하는 메소드입니다.
    private int calculateAge(LocalDateTime birthDate) {
        return Period.between(birthDate.toLocalDate(), LocalDate.now()).getYears();
    }
}