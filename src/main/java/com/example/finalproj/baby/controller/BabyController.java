package com.example.finalproj.baby.controller;

import com.example.finalproj.baby.entity.Baby;
import com.example.finalproj.baby.service.BabyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/baby")
public class BabyController {
    @Autowired
    private BabyService babyService;

    @GetMapping
    public ResponseEntity<List<Baby>> getAllBabies() {
        return ResponseEntity.ok(babyService.getAllBabies());
    }

    @GetMapping("/{babyId}")
    public ResponseEntity<Baby> getBabyById(@PathVariable Integer babyId) {
        return babyService.getBabyById(babyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Baby>> getBabyByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(babyService.getBabyByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Baby> createBaby(@RequestBody Baby baby) {
        return ResponseEntity.ok(babyService.createBaby(baby));
    }

    @PutMapping("/{babyId}")
    public ResponseEntity<Baby> updateBaby(@PathVariable Integer babyId, @RequestBody Baby babyDetails) {
        Baby updatedBaby = babyService.updateBaby(babyId, babyDetails);
        if (updatedBaby == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBaby);
    }

    @DeleteMapping("/{babyId}")
    public ResponseEntity<Void> deleteBaby(@PathVariable Integer babyId) {
        babyService.deleteBaby(babyId);
        return ResponseEntity.ok().build();
    }
}