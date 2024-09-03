package com.example.finalproj.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.example.finalproj.memo.model.dao.MemoDao;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MemoController {

    @Autowired
    private MemoDao memoDao;

    // 메모 목록 페이지를 보여주는 메서드
    @GetMapping("/memo")
    public String memo(Model model) {
        List<Map<String, Object>> memoGroups = memoDao.mtdMemoListGroupByDate();
        model.addAttribute("memoGroups", memoGroups);
        return "memo";
    }

    // 새 메모를 작성하는 메서드
    @PostMapping("/memoWrite")
    @ResponseBody
    public ResponseEntity<Map<String, String>> memoWrite(@RequestBody MemoRequest request) {
        try {
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "내용이 비어있습니다"));
            }
            int result = memoDao.mtdMemoWrite(request.getContent());
            if (result > 0) {
                return ResponseEntity.ok(Map.of("status", "success"));
            } else {
                return ResponseEntity.ok(Map.of("status", "fail", "message", "메모 저장에 실패했습니다"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    // 메모를 수정하는 메서드
    @PostMapping("/memoUpdate")
    @ResponseBody
    public ResponseEntity<Map<String, String>> memoUpdate(@RequestBody MemoRequest request) {
        try {
            // 메모 ID와 내용 유효성 검사
            if (request.getId() == null || request.getId() <= 0) {
                return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "유효하지 않은 메모 ID"));
            }
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "내용이 비어있습니다"));
            }

            // 메모 존재 여부 확인
            Map<String, Object> existingMemo = memoDao.mtdMemoGet(request.getId());
            if (existingMemo == null) {
                return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "해당 메모를 찾을 수 없습니다"));
            }

            // 메모 업데이트
            int result = memoDao.mtdMemoUpdate(request.getId(), request.getContent());
            if (result > 0) {
                return ResponseEntity.ok(Map.of("status", "success"));
            } else {
                return ResponseEntity.ok(Map.of("status", "fail", "message", "메모 수정에 실패했습니다"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    // 메모를 삭제하는 메서드
    @PostMapping("/memoDelete")
    @ResponseBody
    public ResponseEntity<Map<String, String>> memoDelete(@RequestBody MemoRequest request) {
        try {
            // 메모 ID 유효성 검사
            if (request.getId() == null || request.getId() <= 0) {
                return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "유효하지 않은 메모 ID"));
            }

            // 메모 존재 여부 확인
            Map<String, Object> existingMemo = memoDao.mtdMemoGet(request.getId());
            if (existingMemo == null) {
                return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "해당 메모를 찾을 수 없습니다"));
            }

            // 메모 삭제
            int result = memoDao.mtdMemoDelete(request.getId());
            if (result > 0) {
                return ResponseEntity.ok(Map.of("status", "success"));
            } else {
                return ResponseEntity.ok(Map.of("status", "fail", "message", "메모 삭제에 실패했습니다"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    // 메모 검색 메서드 (새로 추가)
    @GetMapping("/memoSearch")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> memoSearch(@RequestParam String searchTerm) {
        try {
            List<Map<String, Object>> searchResults = memoDao.mtdMemoSearch(searchTerm);
            return ResponseEntity.ok(searchResults);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // 메모 요청을 처리하기 위한 내부 클래스
    private static class MemoRequest {
        private Integer id;
        private String content;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}