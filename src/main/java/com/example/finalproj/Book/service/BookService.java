package com.example.finalproj.Book.service;

import com.example.finalproj.Book.entity.Book;
import com.example.finalproj.Book.repository.BookRepository;
import com.example.finalproj.Page.entity.Page;
import com.example.finalproj.AlimInf.entity.AlimInf;
import com.example.finalproj.ml.BookML.BookMLService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.JSONObject;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookMLService bookMLService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    // AlimInf 정보를 기반으로 책 처리
    @Transactional
    public Book processBook(AlimInf alimInf) {
        log.info("책 처리 시작: userId={}, babyId={}", alimInf.getUserId(), alimInf.getBabyId());
        try {
            String mlResponse = bookMLService.sendAlimInfToMLService(alimInf);
            Book processedBook = createOrUpdateBookFromMLResponse(null, mlResponse, alimInf.getUserId());
            log.info("책 처리 완료: bookId={}", processedBook.getBookId());
            return processedBook;
        } catch (Exception e) {
            log.error("책 처리 중 오류 발생: userId={}, babyId={}", alimInf.getUserId(), alimInf.getBabyId(), e);
            throw new RuntimeException("책 처리에 실패했습니다.", e);
        }
    }

    // ML 응답을 기반으로 책 업데이트
    @Transactional
    public Book updateBookWithMLResponse(Integer bookId, String mlResponse) {
        log.info("ML 응답으로 책 업데이트 시작: bookId={}", bookId);
        try {
            Book updatedBook = createOrUpdateBookFromMLResponse(bookId, mlResponse, null);
            log.info("ML 응답으로 책 업데이트 완료: bookId={}", bookId);
            return updatedBook;
        } catch (Exception e) {
            log.error("ML 응답으로 책 업데이트 중 오류 발생: bookId={}", bookId, e);
            throw new RuntimeException("책 업데이트에 실패했습니다.", e);
        }
    }

    // ML 응답을 기반으로 책 생성 또는 업데이트
    private Book createOrUpdateBookFromMLResponse(Integer bookId, String mlResponse, Integer userId) {
        JSONObject jsonResponse = new JSONObject(mlResponse);
        Book book = bookId != null ? bookRepository.findById(bookId).orElse(new Book()) : new Book();

        book.setTitle(jsonResponse.getString("title"));
        if (userId != null) {
            book.setUserId(userId);
        }

        String coverImageBase64 = jsonResponse.getString("cover_image");
        String coverPath = uploadBase64ImageToS3(coverImageBase64, "covers");
        book.setCoverPath(coverPath);

        // 기존 페이지 삭제 (업데이트 시)
        if (bookId != null) {
            book.getPages().clear();
        }

        // 새 페이지 추가
        JSONArray pages = jsonResponse.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject pageJson = pages.getJSONObject(i);
            Page page = new Page();
            page.setBook(book);
            page.setPageNum(i + 1);
            page.setText(pageJson.getString("content"));

            String pageImageBase64 = pageJson.getString("image");
            String pagePath = uploadBase64ImageToS3(pageImageBase64, "pages");
            page.setImagePath(pagePath);

            book.addPage(page);
        }

        return bookRepository.save(book);
    }

    // Base64 이미지를 S3에 업로드
    private String uploadBase64ImageToS3(String base64Image, String folder) {
        byte[] decodedImage = Base64.getDecoder().decode(base64Image.split(",")[1]);
        String fileName = folder + "/" + UUID.randomUUID() + ".png";

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(decodedImage.length);
        metadata.setContentType("image/png");

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedImage)) {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            log.error("S3에 이미지 업로드 실패", e);
            throw new RuntimeException("S3에 이미지 업로드 실패", e);
        }
    }

    // ID로 책 조회
    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    // 모든 책 조회
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 책 업데이트
    public Book updateBook(Integer id, Book bookDetails) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book existingBook = book.get();
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setCoverPath(bookDetails.getCoverPath());
            // 필요에 따라 다른 필드 업데이트
            return bookRepository.save(existingBook);
        }
        return null;
    }

    // 책 삭제
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    // 사용자 ID로 책 조회
    public List<Book> getBooksByUserId(Integer userId) {
        return bookRepository.findByUserId(userId);
    }

    // 동화 생성 메서드
    public String generateFairyTale(AlimInf alimInf) {
        log.info("동화 생성 시작: userId={}, babyId={}", alimInf.getUserId(), alimInf.getBabyId());
        try {
            String mlResponse = bookMLService.sendAlimInfToMLService(alimInf);
            if (mlResponse == null || mlResponse.isEmpty()) {
                throw new RuntimeException("ML 서비스로부터 빈 응답을 받았습니다.");
            }
            log.info("동화 생성 완료: userId={}, babyId={}", alimInf.getUserId(), alimInf.getBabyId());
            return mlResponse;
        } catch (Exception e) {
            log.error("동화 생성 중 오류 발생: userId={}, babyId={}, error={}",
                    alimInf.getUserId(), alimInf.getBabyId(), e.getMessage(), e);
            throw new RuntimeException("동화 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }
}