package com.example.finalproj.redis.service;

import com.example.finalproj.ml.ChatML.ChatMLService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import com.example.finalproj.Chat.entity.ChatMessageDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisChatService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisChatService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void saveMessage(ChatMessageDTO message) {
        String key = getChatKey(message.getUserId(), message.getBabyId());
        redisTemplate.opsForList().rightPush(key, message);
        redisTemplate.expire(key, 24, TimeUnit.HOURS); // 24시간 만료 시간 설정
    }

    public List<ChatMessageDTO> getChatHistory(Long userId, Long babyId) {
        String key = getChatKey(userId, babyId);
        List<Object> rawMessages = redisTemplate.opsForList().range(key, 0, -1);
        List<ChatMessageDTO> chatMessages = new ArrayList<>();

        for (Object rawMessage : rawMessages) {
            if (rawMessage instanceof ChatMessageDTO) {
                chatMessages.add((ChatMessageDTO) rawMessage);
            } else {
                try {
                    ChatMessageDTO message = objectMapper.convertValue(rawMessage, ChatMessageDTO.class);
                    chatMessages.add(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return chatMessages;
    }

    public void deleteChatHistory(Long userId, Long babyId) {
        String key = getChatKey(userId, babyId);
        redisTemplate.delete(key);
    }

    private String getChatKey(Long userId, Long babyId) {
        return "chat:" + userId + ":" + babyId;
    }
}