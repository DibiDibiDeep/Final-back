package com.example.finalproj.Chat.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessageDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String babyId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String sender;

    public ChatMessageDTO() {}

    public ChatMessageDTO(String userId, String babyId, LocalDateTime timestamp, String content, String sender) {
        this.userId = userId;
        this.babyId = babyId;
        this.timestamp = timestamp;
        this.content = content;
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBabyId() {
        return babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "ChatMessageDTO{" +
                "userId='" + userId + '\'' +
                ", babyId='" + babyId + '\'' +
                ", timestamp=" + timestamp +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
