package com.example.finalproj.memo.controller;

import com.example.finalproj.memo.entity.Memo;
import com.example.finalproj.memo.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/memos")
public class MemoController {

    @Autowired
    private MemoService memoService;

    @PostMapping
    public ResponseEntity<Memo> createMemo(@RequestBody Memo memo) {
        return ResponseEntity.ok(memoService.createMemo(memo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Memo> getMemoById(@PathVariable Integer id) {
        return memoService.getMemoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Memo>> getAllMemos() {
        return ResponseEntity.ok(memoService.getAllMemos());
    }

    @GetMapping("/date-range")
    public List<Memo> getMemosByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return memoService.getMemosByDateRange(startDate, endDate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Memo> updateMemo(@PathVariable Integer id, @RequestBody Memo memoDetails) {
        Memo updatedMemo = memoService.updateMemo(id, memoDetails);
        if (updatedMemo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMemo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Integer id) {
        memoService.deleteMemo(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Memo>> getMemosByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Memo> memos = memoService.getMemosByDate(date);
        return ResponseEntity.ok(memos);
    }
}