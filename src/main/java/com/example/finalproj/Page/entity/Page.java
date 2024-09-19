package com.example.finalproj.Page.entity;

import com.example.finalproj.Book.entity.Book;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "Page")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonBackReference
    private Book book;

    private Integer pageNum;

    @Column(length = 1000)
    private String text;

    private String illustPrompt;
    private String imagePath;

    public Page() {}

    public Page(Book book, Integer pageNum, String text, String illustPrompt, String imagePath) {
        this.book = book;
        this.pageNum = pageNum;
        this.text = text;
        this.illustPrompt = illustPrompt;
        this.imagePath = imagePath;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIllustPrompt() {
        return illustPrompt;
    }

    public void setIllustPrompt(String illustPrompt) {
        this.illustPrompt = illustPrompt;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}