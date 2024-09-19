package com.example.finalproj.Page.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.finalproj.Page.entity.Page;
import com.example.finalproj.Page.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public org.springframework.data.domain.Page<Page> getAllPages(Pageable pageable) {
        return pageRepository.findAll(pageable);
    }

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

    public Page updatePage(Integer id, Page pageDetails, MultipartFile image) throws IOException {
        Optional<Page> pageOptional = pageRepository.findById(id);
        if (pageOptional.isPresent()) {
            Page existingPage = pageOptional.get();
            existingPage.setPageNum(pageDetails.getPageNum());
            existingPage.setText(pageDetails.getText());
            existingPage.setIllustPrompt(pageDetails.getIllustPrompt());

            if (image != null && !image.isEmpty()) {
                String imagePath = uploadFileToS3(image, "books/pages");
                existingPage.setImagePath(imagePath);
            }

            return pageRepository.save(existingPage);
        }
        return null;
    }

    public void deletePage(Integer id) {
        pageRepository.deleteById(id);
    }

    private String uploadFileToS3(MultipartFile file, String directory) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String fileName = directory + "/" + UUID.randomUUID() + fileExtension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);

        return s3Client.getUrl(bucketName, fileName).toString();
    }

    private String getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> "." + f.substring(filename.lastIndexOf(".") + 1))
                .orElse("");
    }
}