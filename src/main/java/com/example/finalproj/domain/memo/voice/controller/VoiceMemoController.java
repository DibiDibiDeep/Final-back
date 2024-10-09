package com.example.finalproj.domain.memo.voice.controller;

import com.example.finalproj.domain.memo.voice.entity.VoiceMemo;
import com.example.finalproj.domain.memo.voice.service.VoiceMemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/voice-memos")
public class VoiceMemoController {

    private final VoiceMemoService voiceMemoService;

    public VoiceMemoController(VoiceMemoService voiceMemoService) {
        this.voiceMemoService = voiceMemoService;
    }

    @PostMapping
    public ResponseEntity<?> createVoiceMemo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId,
            @RequestParam("babyId") Integer babyId) {
        try {
            VoiceMemo voiceMemo = voiceMemoService.createVoiceMemo(file, userId, babyId);
            return ResponseEntity.ok(voiceMemo);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("파일 업로드 중 오류 발생: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("예기치 않은 오류 발생: " + e.getMessage());
        }
    }
}