package com.example.finalproj.AlimInf.service;

import com.example.finalproj.AlimInf.entity.AlimInf;
import com.example.finalproj.AlimInf.repository.AlimInfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlimInfService {

    private final AlimInfRepository alimInfRepository;

    @Autowired
    public AlimInfService(AlimInfRepository alimInfRepository) {
        this.alimInfRepository = alimInfRepository;
    }

    // 모든 AlimInf 레코드를 조회
    public List<AlimInf> getAllAlimInfs() {
        return alimInfRepository.findAll();
    }

    // ID로 특정 AlimInf 레코드를 조회
    public Optional<AlimInf> getAlimInfById(Integer id) {
        return alimInfRepository.findById(id);
    }

    // 새로운 AlimInf 레코드를 생성
    public AlimInf createAlimInf(AlimInf alimInf) {
        return alimInfRepository.save(alimInf);
    }

    // ID로 특정 AlimInf 레코드를 수정
    public AlimInf updateAlimInf(Integer id, AlimInf alimInfDetails) {
        Optional<AlimInf> alimInf = alimInfRepository.findById(id);
        if (alimInf.isPresent()) {
            AlimInf existingAlimInf = alimInf.get();
            // 필드를 업데이트
            existingAlimInf.setAlimId(alimInfDetails.getAlimId());
            existingAlimInf.setUserId(alimInfDetails.getUserId());
            existingAlimInf.setBabyId(alimInfDetails.getBabyId());
            existingAlimInf.setTodayId(alimInfDetails.getTodayId());
            existingAlimInf.setName(alimInfDetails.getName());
            existingAlimInf.setEmotion(alimInfDetails.getEmotion());
            existingAlimInf.setHealth(alimInfDetails.getHealth());
            existingAlimInf.setNutrition(alimInfDetails.getNutrition());
            existingAlimInf.setActivities(alimInfDetails.getActivities());
            existingAlimInf.setSpecial(alimInfDetails.getSpecial());
            existingAlimInf.setKeywords(alimInfDetails.getKeywords());
            existingAlimInf.setDiary(alimInfDetails.getDiary());
            existingAlimInf.setDate(alimInfDetails.getDate());
            return alimInfRepository.save(existingAlimInf);
        }
        return null;
    }

    // 특정 AlimInf 레코드를 삭제
    public void deleteAlimInf(Integer id) {
        alimInfRepository.deleteById(id);
    }

    // 특정 날짜 범위 내의 AlimInf 레코드를 조회
    public List<AlimInf> getAlimInfsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return alimInfRepository.findByDateBetween(startDate, endDate);
    }
}
