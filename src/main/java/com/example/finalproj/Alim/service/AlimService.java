package com.example.finalproj.Alim.service;

import com.example.finalproj.Alim.entity.Alim;
import com.example.finalproj.Alim.repository.AlimRepository;
import com.example.finalproj.ml.AlimML.AlimMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AlimService {
    private final AlimRepository alimRepository;
    private final AlimMLService alimMlService;

    @Autowired
    public AlimService(AlimRepository alimRepository, AlimMLService alimMlService) {
        this.alimRepository = alimRepository;
        this.alimMlService = alimMlService;
    }

    // 주어진 날짜로 Alim 목록을 조회
    public List<Alim> getAlimsByDate(LocalDate date) {
        return alimRepository.findByDate(date);
    }

    // 새로운 Alim 생성 및 ML 처리
    public Alim createAlim(Alim alim) {
        Alim savedAlim = alimRepository.save(alim);

        // ML 처리를 비동기적으로 시작
        CompletableFuture.runAsync(() -> alimMlService.processAlim(savedAlim));

        return savedAlim;
    }

    // 주어진 사용자 ID로 Alim을 조회
    public Optional<Alim> getAlimById(Integer userid) {
        return alimRepository.findById(userid);
    }

    // 모든 Alim을 조회
    public List<Alim> getAllAlims() {
        return alimRepository.findAll();
    }

    // 사용자 ID와 날짜 범위로 Alim 목록을 조회
    public List<Alim> getAlimsByUserIdAndDateRange(Integer userId, LocalDateTime start, LocalDateTime end) {
        return alimRepository.findByUserIdAndDateBetween(userId, start, end);
    }

    // Alim 수정
    public Alim updateAlim(Integer id, Alim alimDetails) {
        Optional<Alim> alim = alimRepository.findById(id);
        if (alim.isPresent()) {
            Alim existingAlim = alim.get();
            existingAlim.setUserId(alimDetails.getUserId());
            existingAlim.setBabyId(alimDetails.getBabyId());
            existingAlim.setContent(alimDetails.getContent());
            existingAlim.setDate(alimDetails.getDate());
            return alimRepository.save(existingAlim);
        }
        return null;
    }

    // Alim 삭제
    public void deleteAlim(Integer id) {
        alimRepository.deleteById(id);
    }
}