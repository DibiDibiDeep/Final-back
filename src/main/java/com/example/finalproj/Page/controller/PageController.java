package com.example.finalproj.Page.controller;

import com.example.finalproj.Page.entity.Page;
import com.example.finalproj.Page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageController {
    @Autowired
    private PageService pageService;

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<Page>> getAllPages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        org.springframework.data.domain.Page<Page> pageResult = pageService.getAllPages(PageRequest.of(page, size));
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Page> createPage(@RequestPart("page") Page page,
                                           @RequestPart("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(pageService.createPage(page, image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page> getPageById(@PathVariable Integer id) {
        return pageService.getPageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Page>> getPagesByBookId(@PathVariable Integer bookId) {
        List<Page> pages = pageService.getPagesByBookId(bookId);
        return ResponseEntity.ok(pages);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Page> updatePage(@PathVariable Integer id,
                                           @RequestPart("page") Page pageDetails,
                                           @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        Page updatedPage = pageService.updatePage(id, pageDetails, image);
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