package com.example.finalproj.ml.chatML;

import com.example.finalproj.domain.notice.inference.entity.AlimInf;
import com.example.finalproj.domain.chat.context.entity.ChatMessageDTO;
import com.example.finalproj.domain.memo.entity.Memo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatMLService {


    @Value("${ml.service.url}")
    private String mlServiceUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ChatMLService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendAlimInfToMLService(AlimInf alimInf) {
        Map<String, Object> requestData = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();

        item.put("user_id", alimInf.getUserId());
        item.put("baby_id", alimInf.getBabyId());
        item.put("date", alimInf.getDate().toLocalDate().toString());
        item.put("role", "child");
        item.put("text", alimInf.getDiary());

        items.add(item);
        requestData.put("items", items);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);

        try {
            restTemplate.postForObject(mlServiceUrl + "/embedding", request, String.class);
        } catch (Exception e) {
            // 에러 처리
            e.printStackTrace();
            // 여기서 로깅을 추가하거나 다른 에러 처리 로직을 구현할 수 있습니다.
        }
    }

    public void sendMemoToMLService(Memo memo) {
        Map<String, Object> requestData = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();

        LocalDateTime memoDateTime = memo.getDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        item.put("user_id", memo.getUserId());
        item.put("baby_id", memo.getTodayId()); // TodayId를 baby_id로 사용
        item.put("date", memoDateTime.format(dateFormatter));
        item.put("role", "parents");

        // text 내용 앞에 시분초 추가
        String textWithTime = memoDateTime.format(timeFormatter) + " " + memo.getContent();
        item.put("text", textWithTime);

        // sendToML 필드는 제외

        items.add(item);
        requestData.put("items", items);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);

        try {
            restTemplate.postForObject(mlServiceUrl + "/embedding", request, String.class);
        } catch (Exception e) {
            // 에러 처리
            e.printStackTrace();
            // 여기서 로깅을 추가하거나 다른 에러 처리 로직을 구현할 수 있습니다.
        }
    }

    public ChatMessageDTO getResponse(ChatMessageDTO message, String authToken) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("user_id", message.getUserId());
        requestData.put("baby_id", message.getBabyId());
        requestData.put("text", message.getContent());
        requestData.put("timestamp", message.getTimestamp());
        requestData.put("role", "user");
        requestData.put("session_id", authToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);

        try {
            String endpoint = mlServiceUrl + "/process_query";
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(endpoint, request, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String response = jsonNode.get("response").asText();

                return new ChatMessageDTO(
                        message.getUserId(),
                        message.getBabyId(),
                        message.getTimestamp(),
                        response,
                        "bot",
                        authToken
                );
            } else {
                throw new RuntimeException("ML service returned non-200 status: " + responseEntity.getStatusCodeValue());
            }
        } catch (Exception e) {
            System.err.println("Error processing ML service response: " + e.getMessage());
            throw new RuntimeException("Error communicating with ML service", e);
        }
    }
}
