package com.example.finalproj.EventImg.service;

import com.example.finalproj.EventImg.entity.Image;
import com.example.finalproj.EventImg.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    @Autowired
    public ImageService(ImageRepository imageRepository, S3Service s3Service) {
        this.imageRepository = imageRepository;
        this.s3Service = s3Service;
    }

    @Transactional
    public String uploadAndSaveImage(MultipartFile file) throws IOException {
        String imageUrl = s3Service.uploadFile(file);
        Image image = new Image();
        image.setImageUrl(imageUrl);
        imageRepository.save(image);
        return imageUrl;
    }

    public List<String> getAllImageUrls() {
        return imageRepository.findAll().stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
    }

    public Optional<String> getImageUrlById(Long id) {
        return imageRepository.findById(id)
                .map(Image::getImageUrl);
    }
}