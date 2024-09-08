package com.example.finalproj.AlimInf.controller;

import com.example.finalproj.AlimInf.entity.AlimInf;
import com.example.finalproj.AlimInf.service.AlimInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alim-inf")
public class AlimInfController {

    private final AlimInfService alimInfService;

    @Autowired
    public AlimInfController(AlimInfService alimInfService) {
        this.alimInfService = alimInfService;
    }

    @GetMapping
    public List<AlimInf> getAllAlimInfs() {
        return alimInfService.getAllAlimInfs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimInf> getAlimInfById(@PathVariable Integer id) {
        return alimInfService.getAlimInfById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AlimInf createAlimInf(@RequestBody AlimInf alimInf) {
        return alimInfService.createAlimInf(alimInf);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlimInf> updateAlimInf(@PathVariable Integer id, @RequestBody AlimInf alimInfDetails) {
        AlimInf updatedAlimInf = alimInfService.updateAlimInf(id, alimInfDetails);
        if (updatedAlimInf == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAlimInf);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlimInf(@PathVariable Integer id) {
        alimInfService.deleteAlimInf(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date-range")
    public List<AlimInf> getAlimInfsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return alimInfService.getAlimInfsByDateRange(startDate, endDate);
    }
}