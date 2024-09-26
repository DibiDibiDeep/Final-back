package com.example.finalproj.Chat.service;

import com.example.finalproj.Chat.entity.ChatMessageDTO;
import com.example.finalproj.Chat.repository.ChatRepository;
import com.example.finalproj.ml.ChatML.ChatMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Save the user message
        chatRepository.save(message);

        // Process the message using ML service
        ChatMessageDTO response = chatMLService.getResponse(message);

        // Save the bot response
        chatRepository.save(response);

        return response;
    }
}
