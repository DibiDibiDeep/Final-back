package com.example.finalproj.Page.controller;

import com.example.finalproj.Page.entity.Page;
import com.example.finalproj.Page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageController {

    @Autowired
    private PageService pageService;

    @PostMapping
    public ResponseEntity<Page> createPage(@RequestBody Page page) {
        return ResponseEntity.ok(pageService.createPage(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page> getPageById(@PathVariable Integer id) {
        return pageService.getPageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Page>> getAllPages() {
        return ResponseEntity.ok(pageService.getAllPages());
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Page>> getPagesByBookId(@PathVariable Integer bookId) {
        List<Page> pages = pageService.getPagesByBookId(bookId);
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/book/{bookId}/page/{pageNum}")
    public ResponseEntity<Page> getPageByBookIdAndPageNum(@PathVariable Integer bookId, @PathVariable Integer pageNum) {
        return pageService.getPageByBookIdAndPageNum(bookId, pageNum)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Page> updatePage(@PathVariable Integer id, @RequestBody Page pageDetails) {
        Page updatedPage = pageService.updatePage(id, pageDetails);
        if (updatedPage == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePage(@PathVariable Integer id) {
        pageService.deletePage(id);
        return ResponseEntity.ok().build();
    }
}