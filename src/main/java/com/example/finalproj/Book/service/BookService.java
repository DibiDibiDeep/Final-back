package com.example.finalproj.Book.service;

import com.example.finalproj.Book.entity.Book;
import com.example.finalproj.Book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookRepository.findByDateBetween(startDate, endDate);
    }

    public Book updateBook(Integer id, Book bookDetails) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book existingBook = book.get();
            existingBook.setUserId(bookDetails.getUserId());
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setCoverPath(bookDetails.getCoverPath());
            existingBook.setStartDate(bookDetails.getStartDate());
            existingBook.setEndDate(bookDetails.getEndDate());
            existingBook.setGeneratedDate(bookDetails.getGeneratedDate());
            return bookRepository.save(existingBook);
        }
        return null;
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooksByDate(LocalDate date) {
        return bookRepository.findByDate(date);
    }
}