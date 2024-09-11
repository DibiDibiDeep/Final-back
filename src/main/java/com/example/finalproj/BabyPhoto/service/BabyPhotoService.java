package com.example.finalproj.BabyPhoto.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.finalproj.BabyPhoto.entity.BabyPhoto;
import com.example.finalproj.BabyPhoto.repository.BabyPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BabyPhotoService {

    @Autowired
    private BabyPhotoRepository babyPhotoRepository;

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public List<BabyPhoto> getAllBabyPhotos() {
        return babyPhotoRepository.findAll();
    }

    public Optional<BabyPhoto> getBabyPhotoById(Integer id) {
        return babyPhotoRepository.findById(id);
    }

    public List<BabyPhoto> getBabyPhotosByBabyId(Integer babyId) {
        return babyPhotoRepository.findByBabyId(babyId);
    }

    public List<BabyPhoto> getBabyPhotosByUploadDate(LocalDateTime uploadDate) {
        return babyPhotoRepository.findByUploadDate(uploadDate);
    }

    public BabyPhoto createBabyPhoto(MultipartFile file, Integer babyId) throws IOException {
        String filePath = uploadFileToS3(file);

        BabyPhoto babyPhoto = new BabyPhoto();
        babyPhoto.setBabyId(babyId);
        babyPhoto.setFilePath(filePath);
        babyPhoto.setUploadDate(LocalDateTime.now());

        return babyPhotoRepository.save(babyPhoto);
    }

    private String uploadFileToS3(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        file.delete();
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return UUID.randomUUID().toString() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    public void deleteBabyPhoto(Integer id) {
        babyPhotoRepository.deleteById(id);
    }
}