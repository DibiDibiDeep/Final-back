package com.example.finalproj.BabyPhoto.repository;

import com.example.finalproj.BabyPhoto.entity.BabyPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BabyPhotoRepository extends JpaRepository<BabyPhoto, Integer> {
    List<BabyPhoto> findByUserId(Integer userId);
    List<BabyPhoto> findByBabyId(Integer babyId);
    List<BabyPhoto> findByUploadDate(LocalDateTime uploadDate);
}