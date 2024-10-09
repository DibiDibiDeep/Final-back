package com.example.finalproj.domain.memo.voice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Voice_Memo")
public class VoiceMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voice_memo_id")
    private Integer voiceMemoId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "baby_id")
    private Integer babyId;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "processed")
    private Boolean processed;

    // Constructors, getters, and setters

    public VoiceMemo() {
    }

    public VoiceMemo(Integer userId, Integer babyId, String filePath, LocalDateTime date) {
        this.userId = userId;
        this.babyId = babyId;
        this.filePath = filePath;
        this.date = date;
        this.processed = false;
    }

    public Integer getVoiceMemoId() {
        return voiceMemoId;
    }

    public void setVoiceMemoId(Integer voiceMemoId) {
        this.voiceMemoId = voiceMemoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBabyId() {
        return babyId;
    }

    public void setBabyId(Integer babyId) {
        this.babyId = babyId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    @Override
    public String toString() {
        return "VoiceMemo{" +
                "voiceMemoId=" + voiceMemoId +
                ", userId=" + userId +
                ", babyId=" + babyId +
                ", filePath='" + filePath + '\'' +
                ", date=" + date +
                ", processed=" + processed +
                '}';
    }
}