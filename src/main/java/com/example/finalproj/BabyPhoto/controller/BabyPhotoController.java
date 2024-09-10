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

    @GetMapping
    public ResponseEntity<List<BabyPhoto>> getAllBabyPhotos() {
        return ResponseEntity.ok(babyPhotoService.getAllBabyPhotos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BabyPhoto> getBabyPhotoById(@PathVariable Integer id) {
        return babyPhotoService.getBabyPhotoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BabyPhoto>> getBabyPhotosByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(babyPhotoService.getBabyPhotosByUserId(userId));
    }

    @GetMapping("/baby/{babyId}")
    public ResponseEntity<List<BabyPhoto>> getBabyPhotosByBabyId(@PathVariable Integer babyId) {
        return ResponseEntity.ok(babyPhotoService.getBabyPhotosByBabyId(babyId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<BabyPhoto>> getBabyPhotosByUploadDate(@PathVariable LocalDateTime date) {
        return ResponseEntity.ok(babyPhotoService.getBabyPhotosByUploadDate(date));
    }

    @PostMapping
    public ResponseEntity<BabyPhoto> createBabyPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId,
            @RequestParam("babyId") Integer babyId) throws IOException {
        return ResponseEntity.ok(babyPhotoService.createBabyPhoto(file, userId, babyId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBabyPhoto(@PathVariable Integer id) {
        babyPhotoService.deleteBabyPhoto(id);
        return ResponseEntity.ok().build();
    }
}