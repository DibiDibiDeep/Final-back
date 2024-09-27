package com.example.finalproj.Chat.service;

import com.example.finalproj.Chat.entity.ChatMessageDTO;
import com.example.finalproj.Chat.repository.ChatRepository;
import com.example.finalproj.ml.ChatML.ChatMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMLService chatMLService;

    @Autowired
    public ChatService(ChatRepository chatRepository, ChatMLService chatMLService) {
        this.chatRepository = chatRepository;
        this.chatMLService = chatMLService;
    }

    public ChatMessageDTO processMessage(ChatMessageDTO message) {
        // babyId가 null인 경우 처리
        if (message.getBabyId() == null) {
            System.out.println("message.getBabyId() = " + message.getBabyId());
            // 적절한 기본값 설정 또는 예외 처리
            throw new IllegalArgumentException("babyId cannot be null");
        }

        // Save the user message
        chatRepository.save(message);

        // Process the message using ML service
        ChatMessageDTO response = chatMLService.getResponse(message);

        // babyId가 response에도 제대로 설정되어 있는지 확인
        if (response.getBabyId() == null) {
            response.setBabyId(message.getBabyId());
        }

        // Save the bot response
        chatRepository.save(response);

        return response;
    }

    public List<ChatMessageDTO> getChatHistory(Long userId, Long babyId) {
        return chatRepository.findByUserIdAndBabyId(userId, babyId);
    }
}
