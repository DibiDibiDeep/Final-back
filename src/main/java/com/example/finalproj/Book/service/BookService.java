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
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    // 로깅을 위한 Logger 인스턴스 생성
    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    // 의존성 주입
    @Autowired
    private BookMLService bookMLService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AmazonS3 s3Client;

    // AWS S3 버킷 이름 주입
    @Value("${aws.s3.bucket}")
    private String bucketName;

    // AlimInf 정보를 기반으로 책을 처리하는 메서드
    @Transactional
    public Book processBook(AlimInf alimInf) {
        log.info("책 처리 시작: userId={}, babyId={}", alimInf.getUserId(), alimInf.getBabyId());
        try {
            // ML 서비스에 AlimInf 정보 전송 및 응답 수신
            String mlResponse = bookMLService.sendAlimInfToMLService(alimInf);
            // ML 응답을 기반으로 책 생성
            Book processedBook = createBookFromMLResponse(mlResponse, alimInf.getUserId());
            log.info("책 처리 완료: bookId={}", processedBook.getBookId());
            return processedBook;
        } catch (Exception e) {
            log.error("책 처리 중 오류 발생: userId={}, babyId={}", alimInf.getUserId(), alimInf.getBabyId(), e);
            throw new RuntimeException("책 처리에 실패했습니다.", e);
        }
    }

    // ML 서비스 응답을 기반으로 책을 생성하는 메서드
    @Transactional
    public Book createBookFromMLResponse(String mlResponse, Integer userId) throws IOException {
        JSONObject jsonResponse = new JSONObject(mlResponse);
        Book book = new Book();

        // 책 제목 설정
        book.setTitle(jsonResponse.getString("title"));
        book.setUserId(userId);

        // 책 표지 이미지 처리
        String coverImageUrl = jsonResponse.getString("title_img_path");
        String coverPath = uploadImageFromUrlToS3(coverImageUrl, "covers");
        book.setCoverPath(coverPath);

        // 페이지 처리
        JSONArray pages = jsonResponse.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject pageJson = pages.getJSONObject(i);
            Page page = new Page();
            page.setBook(book);
            page.setPageNum(i + 1);
            page.setText(pageJson.getString("text"));

            // 페이지 이미지 처리
            String pageImageUrl = pageJson.getString("image_url");
            String pagePath = uploadImageFromUrlToS3(pageImageUrl, "pages");
            page.setImagePath(pagePath);

            page.setIllustPrompt(pageJson.getString("illustration_prompt"));

            Book savedBook = bookRepository.save(book);
            log.info("Book saved successfully. Book ID: {}", savedBook.getUserId());

            book.addPage(page);
        }

        // 책 저장 및 반환
        return bookRepository.save(book);

    }

    // URL에서 이미지를 다운로드하여 S3에 업로드하는 메서드
    private String uploadImageFromUrlToS3(String imageUrl, String folder) throws IOException {
        log.info("S3 업로드 시작: URL={}, 폴더={}", imageUrl, folder);
        URL url = new URL(imageUrl);
        String fileName = folder + "/" + UUID.randomUUID() + ".png";

        try (InputStream inputStream = url.openStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");

            log.debug("S3 업로드 시도: 버킷={}, 파일명={}", bucketName, fileName);
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));

            String uploadedUrl = s3Client.getUrl(bucketName, fileName).toString();
            log.info("S3 업로드 성공: URL={}", uploadedUrl);
            return uploadedUrl;
        } catch (IOException e) {
            log.error("S3 업로드 실패: URL={}, 폴더={}, 오류={}", imageUrl, folder, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("S3 업로드 중 예상치 못한 오류 발생: URL={}, 폴더={}, 오류={}", imageUrl, folder, e.getMessage(), e);
            throw new IOException("S3 업로드 중 오류 발생", e);
        }
    }

    // ID로 책을 조회하는 메서드
    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    // 모든 책을 조회하는 메서드
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 책을 업데이트하는 메서드
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

    // 책을 삭제하는 메서드
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    // 사용자 ID로 책을 조회하는 메서드
    public List<Book> getBooksByUserId(Integer userId) {
        return bookRepository.findByUserId(userId);
    }

    // 동화를 생성하는 메서드
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

    // ML 응답을 기반으로 기존 책을 업데이트하는 메서드
    @Transactional
    public Book updateBookWithMLResponse(Integer bookId, String mlResponse) throws IOException {
        // 기존 책 조회
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        JSONObject jsonResponse = new JSONObject(mlResponse);

        // 책 제목 업데이트
        existingBook.setTitle(jsonResponse.getString("title"));

        // 책 표지 이미지 업데이트
        String coverImageUrl = jsonResponse.getString("title_img_path");
        String coverPath = uploadImageFromUrlToS3(coverImageUrl, "covers");
        existingBook.setCoverPath(coverPath);

        // 기존 페이지 삭제
        existingBook.getPages().clear();

        // 새 페이지 추가
        JSONArray pages = jsonResponse.getJSONArray("pages");
        for (int i = 0; i < pages.length(); i++) {
            JSONObject pageJson = pages.getJSONObject(i);
            Page page = new Page();
            page.setBook(existingBook);
            page.setPageNum(i + 1);
            page.setText(pageJson.getString("text"));

            String pageImageUrl = pageJson.getString("image_url");
            String pagePath = uploadImageFromUrlToS3(pageImageUrl, "pages");
            page.setImagePath(pagePath);

            page.setIllustPrompt(pageJson.getString("illustration_prompt"));

            existingBook.addPage(page);
        }

        // 업데이트된 책 저장 및 반환
        return bookRepository.save(existingBook);
    }
}