package com.example.finalproj.domain.memo.voice.repository;

import com.example.finalproj.domain.memo.voice.entity.VoiceMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoiceMemoRepository extends JpaRepository<VoiceMemo, Integer> {
    List<VoiceMemo> findByUserId(Integer userId);
    List<VoiceMemo> findByBabyId(Integer babyId);
    List<VoiceMemo> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<VoiceMemo> findByUserIdAndDateBetween(Integer userId, LocalDateTime startDate, LocalDateTime endDate);
    List<VoiceMemo> findByProcessed(Boolean processed);
}