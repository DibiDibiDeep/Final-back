package com.example.finalproj.BookInf.controller;

import com.example.finalproj.BookInf.entity.BookInf;
import com.example.finalproj.BookInf.service.BookInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-inf")
public class BookInfController {
    @Autowired
    private BookInfService bookInfService;

    @PostMapping("/{bookId}")
    public ResponseEntity<BookInf> saveAndProcessInferenceResult(
            @PathVariable Integer bookId,
            @RequestBody String inferenceResult) {
        BookInf processedResult = bookInfService.saveAndProcessInferenceResult(bookId, inferenceResult);
        return ResponseEntity.ok(processedResult);
    }

    @GetMapping
    public ResponseEntity<List<BookInf>> getAllBookInfs() {
        List<BookInf> bookInfs = bookInfService.getAllBookInfs();
        return ResponseEntity.ok(bookInfs);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookInf> getBookInfByBookId(@PathVariable Integer bookId) {
        return bookInfService.getBookInfByBookId(bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}