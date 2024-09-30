package com.example.finalproj.Chat.service;

import com.example.finalproj.Chat.entity.ChatMessageDTO;
import com.example.finalproj.Chat.repository.ChatRepository;
import com.example.finalproj.ml.ChatML.ChatMLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private static final int MAX_CONTENT_LENGTH = 65535;

    private final ChatRepository chatRepository;
    private final ChatMLService chatMLService;

    @Autowired
    public ChatService(ChatRepository chatRepository, ChatMLService chatMLService) {
        this.chatRepository = chatRepository;
        this.chatMLService = chatMLService;
    }

    public ChatMessageDTO processMessage(ChatMessageDTO message, String authToken) {
        // Validate input
        if (message.getBabyId() == null) {
            logger.error("Received message with null babyId: {}", message);
            throw new IllegalArgumentException("babyId cannot be null");
        }

        // Truncate content if it exceeds the maximum length
        if (message.getContent().length() > MAX_CONTENT_LENGTH) {
            logger.warn("Message content exceeds maximum length. Truncating...");
            message.setContent(message.getContent().substring(0, MAX_CONTENT_LENGTH));
        }

        try {
            // Set sessionId for the user message
            message.setSessionId(authToken);

            // Save the user message
            chatRepository.save(message);
            logger.info("Saved user message: {}", message);

            // Process the message using ML service
            ChatMessageDTO response = chatMLService.getResponse(message, authToken);

            // Ensure babyId is set in the response
            if (response.getBabyId() == null) {
                logger.warn("ML service response had null babyId, setting to original message babyId");
                response.setBabyId(message.getBabyId());
            }

            // Truncate response content if it exceeds the maximum length
            if (response.getContent().length() > MAX_CONTENT_LENGTH) {
                logger.warn("Response content exceeds maximum length. Truncating...");
                response.setContent(response.getContent().substring(0, MAX_CONTENT_LENGTH));
            }

            // Save the bot response
            chatRepository.save(response);
            logger.info("Saved bot response: {}", response);

            return response;
        } catch (Exception e) {
            logger.error("Error processing message: {}", message, e);
            throw new RuntimeException("Error processing chat message", e);
        }
    }

    public List<ChatMessageDTO> getChatHistory(Long userId, Long babyId) {
        logger.info("Fetching chat history for userId: {} and babyId: {}", userId, babyId);
        return chatRepository.findByUserIdAndBabyId(userId, babyId);
    }
}
