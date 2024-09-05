package com.example.finalproj.Alim.controller;

import com.example.finalproj.Alim.entity.Alim;
import com.example.finalproj.Alim.service.AlimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alims")
public class AlimController {
    @Autowired
    private AlimService alimService;

    @GetMapping
    public ResponseEntity<List<Alim>> getAllAlims() {
        return ResponseEntity.ok(alimService.getAllAlims());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alim> getAlimById(@PathVariable Integer id) {
        return alimService.getAlimById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/baby/{babyId}")
    public ResponseEntity<List<Alim>> getAlimsByBabyId(@PathVariable Integer babyId) {
        return ResponseEntity.ok(alimService.getAlimsByBabyId(babyId));
    }

    @GetMapping("/date/{createdAt}")
    public ResponseEntity<List<Alim>> getAlimsByCreatedAt(@PathVariable String createdAt) {
        return ResponseEntity.ok(alimService.getAlimsByCreatedAt(createdAt));
    }

    @PostMapping
    public ResponseEntity<Alim> createAlim(@RequestBody Alim alim) {
        return ResponseEntity.ok(alimService.createAlim(alim));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alim> updateAlim(@PathVariable Integer id, @RequestBody Alim alimDetails) {
        Alim updatedAlim = alimService.updateAlim(id, alimDetails);
        if (updatedAlim == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAlim);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlim(@PathVariable Integer id) {
        alimService.deleteAlim(id);
        return ResponseEntity.ok().build();
    }
}