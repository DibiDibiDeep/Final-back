package com.example.finalproj.Chat.controller;

import com.example.finalproj.Chat.entity.ChatMessageDTO;
import com.example.finalproj.Chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public ResponseEntity<ChatMessageDTO> sendMessage(@RequestBody ChatMessageDTO message) {
        // Validate the incoming message
        if (message.getUserId() == null || message.getContent() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Set the timestamp if it's null
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }

        ChatMessageDTO response = chatService.processMessage(message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{userId}/{babyId}")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistory(@PathVariable Long userId, @PathVariable Long babyId) {
        List<ChatMessageDTO> chatHistory = chatService.getChatHistory(userId, babyId);
        return ResponseEntity.ok(chatHistory);
    }
}
