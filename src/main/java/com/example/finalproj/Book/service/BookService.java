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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookMLService bookMLService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Transactional
    public Book processBook(AlimInf alimInf) {
        String mlResponse = bookMLService.sendAlimInfToMLService(alimInf);
        return createOrUpdateBookFromMLResponse(null, mlResponse, alimInf.getUserId());
    }

    @Transactional
    public Book updateBookWithMLResponse(Integer bookId, String mlResponse) {
        return createOrUpdateBookFromMLResponse(bookId, mlResponse, null);
    }

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

        // Clear existing pages if updating
        if (bookId != null) {
            book.getPages().clear();
        }

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
            throw new RuntimeException("Failed to upload image to S3", e);
        }
    }

    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Integer id, Book bookDetails) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book existingBook = book.get();
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setCoverPath(bookDetails.getCoverPath());
            // Update other fields as necessary
            return bookRepository.save(existingBook);
        }
        return null;
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
}
