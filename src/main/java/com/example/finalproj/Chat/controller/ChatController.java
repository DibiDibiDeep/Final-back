package com.example.finalproj.Chat.controller;

import com.example.finalproj.Chat.entity.ChatMessageDTO;
import com.example.finalproj.Chat.service.ChatService;
import com.example.finalproj.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ChatController(ChatService chatService, JwtTokenProvider jwtTokenProvider) {
        this.chatService = chatService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/send")
    public ResponseEntity<ChatMessageDTO> sendMessage(@RequestBody ChatMessageDTO message, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            System.out.println("Received Authorization header: " + authHeader); // 디버깅을 위한 로그

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("Invalid Authorization header");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            String token = authHeader.substring(7);
            System.out.println("Extracted token: " + token); // 디버깅을 위한 로그

            if (!jwtTokenProvider.validateToken(token)) {
                System.out.println("Token validation failed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            System.out.println("Extracted userId: " + userId);

            if (userId == null || !userId.equals(message.getUserId())) {
                System.out.println("User ID mismatch. Token userId: " + userId + ", Message userId: " + message.getUserId());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            if (message.getBabyId() == null || message.getContent() == null) {
                return ResponseEntity.badRequest().build();
            }

            if (message.getTimestamp() == null) {
                message.setTimestamp(LocalDateTime.now());
            }

            ChatMessageDTO response = chatService.processMessage(message);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("Error in sendMessage: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/history/{userId}/{babyId}")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistory(@PathVariable Long userId,
                                                               @PathVariable Long babyId,
                                                               @RequestHeader("Authorization") String token) {
        // Extract token
        String jwt = token.replace("Bearer ", "");

        // Validate token and extract userId from token
        Long tokenUserId = jwtTokenProvider.getUserIdFromToken(jwt);
        if (tokenUserId == null || !tokenUserId.equals(userId)) {
            return ResponseEntity.badRequest().build();
        }

        List<ChatMessageDTO> chatHistory = chatService.getChatHistory(userId, babyId);
        return ResponseEntity.ok(chatHistory);
    }
}
