package com.example.finalproj.Alim.controller;

import com.example.finalproj.Alim.entity.Alim;
import com.example.finalproj.Alim.service.AlimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alims")
public class AlimController {

    @Autowired
    private AlimService alimService;

    @PostMapping
    public ResponseEntity<Alim> createAlim(@RequestBody Alim alim) {
        return ResponseEntity.ok(alimService.createAlim(alim));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alim> getAlimById(@PathVariable Integer id) {
        return alimService.getAlimById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Alim>> getAllAlims() {
        return ResponseEntity.ok(alimService.getAllAlims());
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Alim>> getAlimsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Alim> alims = alimService.getAlimsByDate(date);
        return ResponseEntity.ok(alims);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Alim>> getAlimsByUserIdAndDateRange(
            @PathVariable Integer userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(alimService.getAlimsByUserIdAndDateRange(userId, start, end));
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