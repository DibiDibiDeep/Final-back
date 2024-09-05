package com.example.finalproj.EventImg.controller;

import com.example.finalproj.EventImg.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String imageUrl = imageService.uploadAndSaveImage(file);
            return ResponseEntity.ok(Map.of("message", "Image uploaded successfully", "url", imageUrl));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Image upload failed"));
        }
    }

    @GetMapping("/urls")
    public ResponseEntity<List<String>> getImageUrls() {
        List<String> imageUrls = imageService.getAllImageUrls();
        return ResponseEntity.ok(imageUrls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getImageUrlById(@PathVariable Long id) {
        return imageService.getImageUrlById(id)
                .map(url -> ResponseEntity.ok(Map.of("url", url)))
                .orElse(ResponseEntity.notFound().build());
    }
}