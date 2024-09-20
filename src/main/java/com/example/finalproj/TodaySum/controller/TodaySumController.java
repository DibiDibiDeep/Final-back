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

    // 모든 TodaySum 레코드를 조회하는 GET 메소드
    @GetMapping
    public List<TodaySum> getAllTodaySums() {
        return todaySumService.getAllTodaySums();
    }

    // ID로 TodaySum 레코드를 조회하는 GET 메소드
    @GetMapping("/{id}")
    public ResponseEntity<TodaySum> getTodaySumById(@PathVariable Integer id) {
        return todaySumService.getTodaySumById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 새로운 TodaySum 레코드를 생성하는 POST 메소드
    @PostMapping
    public TodaySum createTodaySum(@RequestBody TodaySum todaySum) {
        return todaySumService.createTodaySum(todaySum);
    }

    // 기존 TodaySum 레코드를 업데이트하는 PUT 메소드
    @PutMapping("/{id}")
    public ResponseEntity<TodaySum> updateTodaySum(@PathVariable Integer id, @RequestBody TodaySum todaySumDetails) {
        TodaySum updatedTodaySum = todaySumService.updateTodaySum(id, todaySumDetails);
        if (updatedTodaySum == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTodaySum);
    }

    // ID로 TodaySum 레코드를 삭제하는 DELETE 메소드
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodaySum(@PathVariable Integer id) {
        todaySumService.deleteTodaySum(id);
        return ResponseEntity.noContent().build();
    }

    // 주어진 날짜 범위 내의 TodaySum 레코드를 조회하는 GET 메소드
    @GetMapping("/date-range")
    public List<TodaySum> getTodaySumsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return todaySumService.getTodaySumsByDateRange(startDate, endDate);
    }
}
