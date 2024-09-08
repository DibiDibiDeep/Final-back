package com.example.finalproj.FairyTale.controller;

import com.example.finalproj.FairyTale.entity.FairyTale;
import com.example.finalproj.FairyTale.service.FairyTaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fairy-tale")
public class FairyTaleController {

    private final FairyTaleService fairyTaleService;

    @Autowired
    public FairyTaleController(FairyTaleService fairyTaleService) {
        this.fairyTaleService = fairyTaleService;
    }

    @GetMapping
    public List<FairyTale> getAllFairyTales() {
        return fairyTaleService.getAllFairyTales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FairyTale> getFairyTaleById(@PathVariable Integer id) {
        return fairyTaleService.getFairyTaleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FairyTale createFairyTale(@RequestBody FairyTale fairyTale) {
        return fairyTaleService.createFairyTale(fairyTale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FairyTale> updateFairyTale(@PathVariable Integer id, @RequestBody FairyTale fairyTaleDetails) {
        FairyTale updatedFairyTale = fairyTaleService.updateFairyTale(id, fairyTaleDetails);
        if (updatedFairyTale == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFairyTale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFairyTale(@PathVariable Integer id) {
        fairyTaleService.deleteFairyTale(id);
        return ResponseEntity.noContent().build();
    }
}