package com.example.finalproj.BabyPhoto.repository;

import com.example.finalproj.BabyPhoto.entity.BabyPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BabyPhotoRepository extends JpaRepository<BabyPhoto, Integer> {
    // 특정 아기의 모든 사진 찾기
    List<BabyPhoto> findByBabyId(Integer babyId);

    // 특정 날짜에 업로드된 사진 찾기
    List<BabyPhoto> findByUploadDate(LocalDateTime uploadDate);

//    BabyPhoto edit(BabyPhoto babyPhoto);
}