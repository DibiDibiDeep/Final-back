package com.example.finalproj.ml.service;

import com.example.finalproj.Book.entity.Book;
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
import java.util.Map;

@Service
public class MLService {
    @Value("${ml.service.url}")
    private String mlServiceUrl;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public MLService(RestTemplate restTemplate, ApplicationEventPublisher eventPublisher, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    // 이미지를 ML 서비스로 전송
    public void sendImageToMLService(String imagePath, Integer userId, Integer babyId, Integer calendarPhotoId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"image_path\": \"%s\", \"user_id\": %d, \"baby_id\": %d}",
                imagePath, userId, babyId);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        try {
            String mlResponse = restTemplate.postForObject(mlServiceUrl + "/process_image", request, String.class);
            System.out.println("ML Service response: " + mlResponse);
            eventPublisher.publishEvent(new CalendarMLProcessingCompletedEvent(this, mlResponse, calendarPhotoId));
        } catch (Exception e) {
            System.err.println("Error in ML service process: " + e.getMessage());
        }
    }

    // 책 정보를 ML 서비스로 전송
    public void sendBookToMLService(Book book) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"book_id\": %d, \"user_id\": %d, \"title\": \"%s\", \"cover_path\": \"%s\"}",
                book.getBookId(), book.getUserId(), book.getTitle(), book.getCoverPath());
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        try {
            String mlResponse = restTemplate.postForObject(mlServiceUrl + "/process_book", request, String.class);
            System.out.println("ML Service response for book: " + mlResponse);
            eventPublisher.publishEvent(new BookMLProcessingCompletedEvent(this, mlResponse, book.getBookId()));
        } catch (Exception e) {
            System.err.println("Error in ML service process for book: " + e.getMessage());
        }
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
        return restTemplate.postForObject(mlServiceUrl + "/process_alim", request, String.class);
    }

    public static AlimInf createAlimInfFromMLResponse(String mlResponse, Integer alimId) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(mlResponse, Map.class);

        AlimInf alimInf = new AlimInf();
        alimInf.setAlimId(alimId);
        alimInf.setName((String) responseMap.get("name"));
        alimInf.setEmotion((String) responseMap.get("emotion"));
        alimInf.setHealth((String) responseMap.get("health"));
        alimInf.setNutrition((String) responseMap.get("nutrition"));
        alimInf.setActivities((String) responseMap.get("activities").toString());
        alimInf.setSpecial((String) responseMap.get("special"));
        alimInf.setKeywords((String) responseMap.get("keywords").toString());
        alimInf.setDiary((String) responseMap.get("diary"));
        alimInf.setDate(LocalDateTime.now());

        return alimInf;
    }
}