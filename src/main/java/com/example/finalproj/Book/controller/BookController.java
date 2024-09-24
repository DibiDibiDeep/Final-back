package com.example.finalproj.Book.controller;

import com.example.finalproj.Book.entity.Book;
import com.example.finalproj.Book.service.BookService;
import com.example.finalproj.AlimInf.entity.AlimInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping(value = "/process_book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> processBook(@RequestBody AlimInf alimInf) {
        Book processedBook = bookService.processBook(alimInf);
        return ResponseEntity.ok(processedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Integer id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        if (updatedBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // userId로 책을 조회하는 엔드포인트 추가
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Book>> getBooksByUserId(@PathVariable Integer userId) {
        List<Book> books = bookService.getBooksByUserId(userId);
        return ResponseEntity.ok(books);
    }
}