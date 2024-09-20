package com.example.finalproj.baby.service;

import com.example.finalproj.baby.entity.Baby;
import com.example.finalproj.baby.repository.BabyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BabyService {
    @Autowired
    private BabyRepository babyRepository;

    // 모든 아기 정보 조회
    public List<Baby> getAllBabies() {
        return babyRepository.findAll();
    }

    // 특정 아기 정보 조회
    public Optional<Baby> getBabyById(Integer babyId) {
        return babyRepository.findById(babyId);
    }

    // 사용자 ID로 아기 정보 조회
    public List<Baby> getBabyByUserId(Integer userId) {
        return babyRepository.findByUserId(userId);
    }

    // 새로운 아기 정보 생성
    public Baby createBaby(Baby baby) {
        return babyRepository.save(baby);
    }

    // 아기 정보 수정
    public Baby updateBaby(Integer babyId, Baby babyDetails) {
        Optional<Baby> baby = babyRepository.findById(babyId);
        if (baby.isPresent()) {
            Baby updatedBaby = baby.get();
            updatedBaby.setBabyName(babyDetails.getBabyName());
            updatedBaby.setBirth(babyDetails.getBirth());
            updatedBaby.setGender(babyDetails.getGender());
            updatedBaby.setUserId(babyDetails.getUserId());
            return babyRepository.save(updatedBaby);
        }
        return null;
    }

    // 아기 정보 삭제
    public void deleteBaby(Integer babyId) {
        babyRepository.deleteById(babyId);
    }

    // 사용자가 아기 정보를 가지고 있는지 확인
    public boolean userHasBaby(Integer userId) {
        return babyRepository.existsByUserId(userId);
    }
}