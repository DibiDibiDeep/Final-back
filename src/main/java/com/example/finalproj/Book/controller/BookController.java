package com.example.finalproj.Book.controller;

import com.example.finalproj.Book.entity.Book;
import com.example.finalproj.Book.service.BookService;
import com.example.finalproj.AlimInf.entity.AlimInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 책 처리 엔드포인트
    @PostMapping(value = "/process_book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> processBook(@RequestBody AlimInf alimInf) {
        Book processedBook = bookService.processBook(alimInf);
        return ResponseEntity.ok(processedBook);
    }

    // ID로 책 조회 엔드포인트
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Integer id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 모든 책 조회 엔드포인트
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // 책 업데이트 엔드포인트
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        if (updatedBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBook);
    }

    // 책 삭제 엔드포인트
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // 사용자 ID로 책 조회 엔드포인트
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Book>> getBooksByUserId(@PathVariable Integer userId) {
        List<Book> books = bookService.getBooksByUserId(userId);
        return ResponseEntity.ok(books);
    }

    // 동화 생성 엔드포인트
    @PostMapping("/generate_fairytale")
    public ResponseEntity<?> generateFairyTale(@RequestBody AlimInf alimInf) {
        try {
            String fairyTale = bookService.generateFairyTale(alimInf);
            return ResponseEntity.ok(fairyTale);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("동화 생성 실패: " + e.getMessage());
        }
    }
}