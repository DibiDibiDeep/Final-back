package com.example.finalproj.BabyPhoto.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BabyPhotoService {

    // BabyPhotoRepository 주입
    @Autowired
    private BabyPhotoRepository babyPhotoRepository;

    // AmazonS3 클라이언트 주입
    @Autowired
    private AmazonS3 s3Client;

    // S3 버킷 이름 주입
    @Value("${aws.s3.bucket}")
    private String bucketName;

    // 모든 아기 사진 조회
    public List<BabyPhoto> getAllBabyPhotos() {
        return babyPhotoRepository.findAll();
    }

    // ID로 특정 아기 사진 조회
    public Optional<BabyPhoto> getBabyPhotoById(Integer id) {
        return babyPhotoRepository.findById(id);
    }

    // 아기 ID로 해당 아기의 모든 사진 조회
    public List<BabyPhoto> getBabyPhotosByBabyId(Integer babyId) {
        return babyPhotoRepository.findByBabyId(babyId);
    }

    // 업로드 날짜로 아기 사진 조회
    public List<BabyPhoto> getBabyPhotosByUploadDate(LocalDateTime uploadDate) {
        return babyPhotoRepository.findByUploadDate(uploadDate);
    }

    // 새로운 아기 사진 생성 및 S3에 업로드
    public BabyPhoto createBabyPhoto(MultipartFile file, Integer babyId) throws IOException {
        String fileUrl = uploadFileToS3(file);

        BabyPhoto babyPhoto = new BabyPhoto();
        babyPhoto.setBabyId(babyId);
        babyPhoto.setFilePath(fileUrl);
        babyPhoto.setUploadDate(LocalDateTime.now());

        return babyPhotoRepository.save(babyPhoto);
    }

    // 파일을 S3에 업로드하고 URL 반환
    private String uploadFileToS3(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        file.delete();
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    // MultipartFile을 File로 변환
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    // 유니크한 파일 이름 생성
    private String generateFileName(MultipartFile multiPart) {
        return UUID.randomUUID().toString() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    // 아기 사진 삭제
    public void deleteBabyPhoto(Integer id) {
        babyPhotoRepository.deleteById(id);
    }
    
    // 아기 사진 수정
//    public BabyPhoto updateBabyPhoto(MultipartFile file, Integer babyId) throws IOException {
//        String fileUrl = uploadFileToS3(file);
//
//        BabyPhoto babyPhoto = new BabyPhoto();
//        babyPhoto.setBabyId(babyId);
//        babyPhoto.setFilePath(fileUrl);
//        babyPhoto.setUploadDate(LocalDateTime.now());
//
//        return babyPhotoRepository.edit(babyPhoto);
//    }
}