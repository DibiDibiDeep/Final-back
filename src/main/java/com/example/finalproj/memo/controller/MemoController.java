package com.example.finalproj.memo.controller;
import com.example.finalproj.memo.entity.Memo;
import com.example.finalproj.memo.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/memos")
public class MemoController {
    @Autowired
    private MemoService memoService;

    @GetMapping
    public ResponseEntity<List<Memo>> getAllMemos() {
        return ResponseEntity.ok(memoService.getAllMemos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Memo> getMemoById(@PathVariable Integer id) {
        return memoService.getMemoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Memo>> getMemosByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(memoService.getMemosByUserId(userId));
    }

    @GetMapping("/today/{todayId}")
    public ResponseEntity<List<Memo>> getMemosByTodayId(@PathVariable Integer todayId) {
        return ResponseEntity.ok(memoService.getMemosByTodayId(todayId));
    }

    @GetMapping("/calendar/{calendarId}")
    public ResponseEntity<List<Memo>> getMemosByCalendarId(@PathVariable Integer calendarId) {
        return ResponseEntity.ok(memoService.getMemosByCalendarId(calendarId));
    }

    @GetMapping("/date/{createdAt}")
    public ResponseEntity<List<Memo>> getMemosByCreatedAt(@PathVariable String createdAt) {
        return ResponseEntity.ok(memoService.getMemosByCreatedAt(createdAt));
    }

    @PostMapping
    public ResponseEntity<Memo> createMemo(@RequestBody Memo memo) {
        return ResponseEntity.ok(memoService.createMemo(memo));
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
}