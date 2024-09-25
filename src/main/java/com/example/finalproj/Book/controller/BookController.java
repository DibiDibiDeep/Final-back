package com.example.finalproj.Book.controller;

import com.example.finalproj.Book.entity.Book;
import com.example.finalproj.Book.service.BookService;
import com.example.finalproj.AlimInf.entity.AlimInf;
import com.example.finalproj.AlimInf.service.AlimInfService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private AlimInfService alimInfService;

    @Autowired
    private ObjectMapper objectMapper;

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

    // 동화 생성 엔드포인트 (AlimInf ID로 데이터 조회 후 ML 전송)
    @PostMapping("/generate_fairytale/{alimInfId}")
    public ResponseEntity<?> generateFairyTale(@PathVariable Integer alimInfId) {
        try {
            // AlimInf ID로 데이터를 가져옴 (Optional로 처리)
            Optional<AlimInf> optionalAlimInf = alimInfService.getAlimInfById(alimInfId);

            // AlimInf가 존재하지 않으면 에러 반환
            if (optionalAlimInf.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("해당 ID의 AlimInf 정보를 찾을 수 없습니다.");
            }

            // Optional에서 AlimInf 객체를 가져옴
            AlimInf alimInf = optionalAlimInf.get();

            // 가져온 AlimInf 데이터를 이용해 ML 모델에 전송
            String fairyTale = bookService.generateFairyTale(alimInf);

            // FastAPI inference 결과(JSON) 로그 출력
            logger.info("FastAPI Inference 결과: {}", fairyTale);

            // JSON 문자열을 객체로 파싱
            Object jsonObject = objectMapper.readValue(fairyTale, Object.class);

            // JSON을 보기 좋게 포맷팅
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

            // 포맷팅된 JSON 로그 출력
            logger.info("포맷팅된 FastAPI Inference 결과:\n{}", prettyJson);

            // 생성된 동화를 JSON 형태로 응답으로 반환
            return ResponseEntity.ok(jsonObject);
        } catch (Exception e) {
            logger.error("동화 생성 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("동화 생성 실패: " + e.getMessage());
        }
    }
}