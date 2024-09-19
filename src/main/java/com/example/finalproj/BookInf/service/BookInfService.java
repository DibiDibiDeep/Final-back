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

    @Transactional
    public BookInf saveAndProcessInferenceResult(Integer bookId, String inferenceResult) {
        BookInf bookInf = new BookInf();
        bookInf.setBookId(bookId);
        bookInf.setInferenceResult(inferenceResult);
        bookInf.setInferenceDate(LocalDateTime.now());

        try {
            Map<String, Object> resultMap = objectMapper.readValue(inferenceResult, Map.class);
            bookInf.setUserId(Integer.parseInt((String) resultMap.get("user_id")));
            // Process other inference results as needed
        } catch (Exception e) {
            throw new IllegalArgumentException("Error processing inference result", e);
        }

        return bookInfRepository.save(bookInf);
    }

    public List<BookInf> getAllBookInfs() {
        return bookInfRepository.findAll();
    }

    public Optional<BookInf> getBookInfByBookId(Integer bookId) {
        return bookInfRepository.findByBookId(bookId);
    }
}