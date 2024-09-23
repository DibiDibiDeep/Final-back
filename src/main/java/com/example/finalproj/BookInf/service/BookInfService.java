package com.example.finalproj.BookInf.service;

import com.example.finalproj.BookInf.entity.BookInf;
import com.example.finalproj.BookInf.repository.BookInfRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookInfService {
    @Autowired
    private BookInfRepository bookInfRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // 추론 결과 저장 및 처리
    @Transactional
    public BookInf saveAndProcessInferenceResult(Integer bookId, String inferenceResult) {
        BookInf bookInf = new BookInf();
        bookInf.setBookId(bookId);
        bookInf.setInferenceResult(inferenceResult);
        bookInf.setInferenceDate(LocalDateTime.now());

        try {
            // JSON 문자열을 Map으로 변환
            Map<String, Object> resultMap = objectMapper.readValue(inferenceResult, Map.class);
            bookInf.setUserId(Integer.parseInt((String) resultMap.get("user_id")));
            // 필요한 경우 추론 결과에서 추가 정보 처리
        } catch (Exception e) {
            throw new IllegalArgumentException("Error processing inference result", e);
        }

        return bookInfRepository.save(bookInf);
    }

    // 모든 BookInf 조회
    public List<BookInf> getAllBookInfs() {
        return bookInfRepository.findAll();
    }

    // 책 ID로 BookInf 조회
    public Optional<BookInf> getBookInfByBookId(Integer bookId) {
        return bookInfRepository.findByBookId(bookId);
    }
}