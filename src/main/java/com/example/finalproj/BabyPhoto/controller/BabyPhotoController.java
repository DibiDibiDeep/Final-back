package com.example.finalproj.BabyPhoto.controller;

import com.example.finalproj.BabyPhoto.entity.BabyPhoto;
import com.example.finalproj.BabyPhoto.service.BabyPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/baby-photos")
public class BabyPhotoController {

    @Autowired
    private BabyPhotoService babyPhotoService;

    // 모든 아기 사진 조회
    @GetMapping
    public ResponseEntity<List<BabyPhoto>> getAllBabyPhotos() {
        return ResponseEntity.ok(babyPhotoService.getAllBabyPhotos());
    }

    // 특정 아기 사진 조회
    @GetMapping("/{id}")
    public ResponseEntity<BabyPhoto> getBabyPhotoById(@PathVariable Integer id) {
        return babyPhotoService.getBabyPhotoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 특정 아기의 모든 사진 조회
    @GetMapping("/baby/{babyId}")
    public ResponseEntity<List<BabyPhoto>> getBabyPhotosByBabyId(@PathVariable Integer babyId) {
        return ResponseEntity.ok(babyPhotoService.getBabyPhotosByBabyId(babyId));
    }

    // 특정 날짜에 업로드된 사진 조회
    @GetMapping("/date/{date}")
    public ResponseEntity<List<BabyPhoto>> getBabyPhotosByUploadDate(@PathVariable LocalDateTime date) {
        return ResponseEntity.ok(babyPhotoService.getBabyPhotosByUploadDate(date));
    }

    // 새로운 아기 사진 업로드
    @PostMapping
    public ResponseEntity<?> createBabyPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("babyId") Integer babyId) {
        try {
            String contentType = file.getContentType();
            if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
                return ResponseEntity.badRequest().body("JPG와 PNG 파일만 허용됩니다.");
            }
            BabyPhoto babyPhoto = babyPhotoService.createBabyPhoto(file, babyId);
            return ResponseEntity.ok(babyPhoto);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("파일 업로드 실패: " + e.getMessage());
        }
    }

    // 아기 사진 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBabyPhoto(@PathVariable Integer id) {
        babyPhotoService.deleteBabyPhoto(id);
        return ResponseEntity.ok().build();
    }
}