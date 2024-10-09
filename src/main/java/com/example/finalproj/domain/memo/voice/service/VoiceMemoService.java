package com.example.finalproj.domain.memo.voice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.finalproj.domain.memo.voice.entity.VoiceMemo;
import com.example.finalproj.domain.memo.voice.repository.VoiceMemoRepository;
import com.example.finalproj.ml.voiceML.VoiceMLService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VoiceMemoService {

    private final VoiceMemoRepository voiceMemoRepository;
    private final AmazonS3 s3Client;
    private final VoiceMLService voiceMLService;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public VoiceMemoService(VoiceMemoRepository voiceMemoRepository,
                            AmazonS3 s3Client,
                            VoiceMLService voiceMLService) {
        this.voiceMemoRepository = voiceMemoRepository;
        this.s3Client = s3Client;
        this.voiceMLService = voiceMLService;
    }

    public VoiceMemo createVoiceMemo(MultipartFile file, Integer userId, Integer babyId) throws IOException {
        String filePath = uploadFileToS3(file);

        VoiceMemo voiceMemo = new VoiceMemo();
        voiceMemo.setUserId(userId);
        voiceMemo.setBabyId(babyId);
        voiceMemo.setFilePath(filePath);
        voiceMemo.setDate(LocalDateTime.now());
        voiceMemo.setProcessed(false);

        VoiceMemo savedVoiceMemo = voiceMemoRepository.save(voiceMemo);

        // ML 서비스로 음성 파일 전송 (비동기 처리)
        voiceMLService.sendVoiceToMLService(filePath, userId, babyId, savedVoiceMemo.getVoiceMemoId());

        return savedVoiceMemo;
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
}