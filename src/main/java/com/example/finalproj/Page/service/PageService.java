package com.example.finalproj.Page.service;

import com.example.finalproj.Book.entity.Book;
import com.example.finalproj.Book.repository.BookRepository;
import com.example.finalproj.Page.entity.Page;
import com.example.finalproj.Page.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private BookRepository bookRepository;

    public Page createPage(Page page) {
        Book book = bookRepository.findById(page.getBook().getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + page.getBook().getBookId()));
        page.setBook(book);
        return pageRepository.save(page);
    }

    public Optional<Page> getPageById(Integer id) {
        return pageRepository.findById(id);
    }

    public List<Page> getAllPages() {
        return pageRepository.findAll();
    }

    public List<Page> getPagesByBookId(Integer bookId) {
        return pageRepository.findByBookBookIdOrderByPageNum(bookId);
    }

    public Optional<Page> getPageByBookIdAndPageNum(Integer bookId, Integer pageNum) {
        return pageRepository.findByBookBookIdAndPageNum(bookId, pageNum);
    }

    public Page updatePage(Integer id, Page pageDetails) {
        Page existingPage = pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Page not found with id: " + id));

        Book book = bookRepository.findById(pageDetails.getBook().getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + pageDetails.getBook().getBookId()));

        existingPage.setBook(book);
        existingPage.setPageNum(pageDetails.getPageNum());
        existingPage.setText(pageDetails.getText());
        existingPage.setIllustPrompt(pageDetails.getIllustPrompt());
        existingPage.setImagePath(pageDetails.getImagePath());

        return pageRepository.save(existingPage);
    }

    public void deletePage(Integer id) {
        pageRepository.deleteById(id);
    }
}