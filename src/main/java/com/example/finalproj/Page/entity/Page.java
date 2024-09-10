package com.example.finalproj.Page.entity;

import com.example.finalproj.Book.entity.Book;
import jakarta.persistence.*;

@Entity
@Table(name = "Page")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_id")
    private Integer pageId;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "page_num", nullable = false)
    private Integer pageNum;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "illust_prompt", nullable = false)
    private String illustPrompt;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    // Constructors, getters, and setters

    public Page() {}

    public Page(Book book, Integer pageNum, String text, String illustPrompt, String imagePath) {
        this.book = book;
        this.pageNum = pageNum;
        this.text = text;
        this.illustPrompt = illustPrompt;
        this.imagePath = imagePath;
    }

    // Getters and setters for all fields

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