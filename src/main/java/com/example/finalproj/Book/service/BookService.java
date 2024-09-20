package com.example.finalproj.Book.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.finalproj.Book.entity.Book;
import com.example.finalproj.Book.repository.BookRepository;
import com.example.finalproj.ml.service.MLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private MLService mlService;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    // 새 책 생성
    public Book createBook(Book book, MultipartFile coverImage) throws IOException {
        if (coverImage != null && !coverImage.isEmpty()) {
            String coverPath = uploadFileToS3(coverImage, "books/covers");
            book.setCoverPath(coverPath);
        }
        Book savedBook = bookRepository.save(book);

        // ML 서비스로 책 정보 전송
        mlService.sendBookToMLService(savedBook);

        return savedBook;
    }

    // ID로 책 조회
    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findByIdWithPages(id);
    }

    // 모든 책 조회
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 책 정보 업데이트
    public Book updateBook(Integer id, Book bookDetails) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book existingBook = book.get();
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setCoverPath(bookDetails.getCoverPath());
            existingBook.setStartDate(bookDetails.getStartDate());
            existingBook.setEndDate(bookDetails.getEndDate());
            return bookRepository.save(existingBook);
        }
        return null;
    }

    // 책 삭제
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    // S3에 파일 업로드
    private String uploadFileToS3(MultipartFile file, String directory) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String fileName = directory + "/" + UUID.randomUUID() + fileExtension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);

        return s3Client.getUrl(bucketName, fileName).toString();
    }

    // 파일 확장자 추출
    private String getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> "." + f.substring(filename.lastIndexOf(".") + 1))
                .orElse("");
    }

    // ML 응답으로 책 정보 업데이트
    public void updateBookWithMLResponse(Integer bookId, String mlResponse) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            // ML 응답을 파싱하고 필요한 정보를 추출하여 책 정보 업데이트
            // 예: book.setMlGeneratedTitle(extractTitleFromMLResponse(mlResponse));
            bookRepository.save(book);
        } else {
            throw new RuntimeException("Book not found with id: " + bookId);
        }
    }

    // ML 응답에서 필요한 정보를 추출하는 메서드들...
}