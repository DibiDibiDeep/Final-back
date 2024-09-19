package com.example.finalproj.Page.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.finalproj.Page.entity.Page;
import com.example.finalproj.Page.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PageService {
    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public Page createPage(Page page, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String imagePath = uploadFileToS3(image, "books/pages");
            page.setImagePath(imagePath);
        }
        return pageRepository.save(page);
    }

    public Optional<Page> getPageById(Integer id) {
        return pageRepository.findById(id);
    }

    public List<Page> getPagesByBookId(Integer bookId) {
        return pageRepository.findByBookBookIdOrderByPageNum(bookId);
    }

    public Page updatePage(Integer id, Page pageDetails) {
        Optional<Page> page = pageRepository.findById(id);
        if (page.isPresent()) {
            Page existingPage = page.get();
            existingPage.setPageNum(pageDetails.getPageNum());
            existingPage.setText(pageDetails.getText());
            existingPage.setIllustPrompt(pageDetails.getIllustPrompt());
            existingPage.setImagePath(pageDetails.getImagePath());
            return pageRepository.save(existingPage);
        }
        return null;
    }

    public void deletePage(Integer id) {
        pageRepository.deleteById(id);
    }

    private String uploadFileToS3(MultipartFile file, String directory) throws IOException {
        File convertedFile = convertMultiPartToFile(file);
        String fileName = directory + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
        convertedFile.delete();
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }
}