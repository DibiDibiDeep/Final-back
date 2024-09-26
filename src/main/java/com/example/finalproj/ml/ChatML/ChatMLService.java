package com.example.finalproj.ml.ChatML;

import com.example.finalproj.AlimInf.entity.AlimInf;
import com.example.finalproj.Chat.entity.ChatMessageDTO;
import com.example.finalproj.memo.entity.Memo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    public ChatMLService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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

    public ChatMessageDTO getResponse(ChatMessageDTO message) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("user_id", message.getUserId());
        requestData.put("baby_id", message.getBabyId());
        requestData.put("text", message.getContent());
        requestData.put("role", "user");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);

        try {
            String mlResponse = restTemplate.postForObject(mlServiceUrl + "/chat", request, String.class);
            return new ChatMessageDTO(
                    message.getUserId(),
                    message.getBabyId(),
                    message.getTimestamp(),
                    mlResponse,
                    "bot"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ChatMessageDTO(
                    message.getUserId(),
                    message.getBabyId(),
                    message.getTimestamp(),
                    "죄송합니다. 응답을 생성하는 데 문제가 발생했습니다.",
                    "bot"
            );
        }
    }
}
