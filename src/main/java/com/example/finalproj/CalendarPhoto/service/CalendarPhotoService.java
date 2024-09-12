package com.example.finalproj.CalendarPhoto.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.finalproj.CalendarPhoto.entity.CalendarPhoto;
import com.example.finalproj.CalendarPhoto.repository.CalendarPhotoRepository;
import com.example.finalproj.ml.service.MLService;
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
public class CalendarPhotoService {

    @Autowired
    private CalendarPhotoRepository calendarPhotoRepository;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private MLService mlService;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public List<CalendarPhoto> getAllCalendarPhotos() {
        return calendarPhotoRepository.findAll();
    }

    public Optional<CalendarPhoto> getCalendarPhotoById(Integer id) {
        return calendarPhotoRepository.findById(id);
    }

    public List<CalendarPhoto> getCalendarPhotosByUserId(Integer userId) {
        return calendarPhotoRepository.findByUserId(userId);
    }

    public List<CalendarPhoto> getCalendarPhotosByBabyId(Integer babyId) {
        return calendarPhotoRepository.findByBabyId(babyId);
    }

    public List<CalendarPhoto> getCalendarPhotosByDate(LocalDateTime date) {
        return calendarPhotoRepository.findByDate(date);
    }

    public List<CalendarPhoto> getCalendarPhotosByYearAndMonth(int year, int month) {
        return calendarPhotoRepository.findByYearAndMonth(year, month);
    }

    public CalendarPhoto createCalendarPhoto(MultipartFile file, Integer userId, Integer babyId, LocalDateTime date) throws IOException {
        String filePath = uploadFileToS3(file);

        CalendarPhoto calendarPhoto = new CalendarPhoto();
        calendarPhoto.setUserId(userId);
        calendarPhoto.setBabyId(babyId);
        calendarPhoto.setFilePath(filePath);
        calendarPhoto.setDate(date);

        CalendarPhoto savedPhoto = calendarPhotoRepository.save(calendarPhoto);

        try {
            mlService.sendImageToMLService(filePath, userId, babyId);
        } catch (Exception e) {
            System.err.println("Failed to send image to ML service: " + e.getMessage());
        }

        return savedPhoto;
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

    public void deleteCalendarPhoto(Integer id) {
        calendarPhotoRepository.deleteById(id);
    }
}