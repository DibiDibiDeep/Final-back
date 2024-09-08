package com.example.finalproj.TodaySum.controller;

import com.example.finalproj.TodaySum.entity.TodaySum;
import com.example.finalproj.TodaySum.service.TodaySumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/today-sum")
public class TodaySumController {

    private final TodaySumService todaySumService;

    @Autowired
    public TodaySumController(TodaySumService todaySumService) {
        this.todaySumService = todaySumService;
    }

    @GetMapping
    public List<TodaySum> getAllTodaySums() {
        return todaySumService.getAllTodaySums();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodaySum> getTodaySumById(@PathVariable Integer id) {
        return todaySumService.getTodaySumById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TodaySum createTodaySum(@RequestBody TodaySum todaySum) {
        return todaySumService.createTodaySum(todaySum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodaySum> updateTodaySum(@PathVariable Integer id, @RequestBody TodaySum todaySumDetails) {
        TodaySum updatedTodaySum = todaySumService.updateTodaySum(id, todaySumDetails);
        if (updatedTodaySum == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTodaySum);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodaySum(@PathVariable Integer id) {
        todaySumService.deleteTodaySum(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date-range")
    public List<TodaySum> getTodaySumsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return todaySumService.getTodaySumsByDateRange(startDate, endDate);
    }
}