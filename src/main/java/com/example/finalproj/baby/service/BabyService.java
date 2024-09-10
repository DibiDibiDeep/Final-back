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

    public List<Baby> getAllBabies() {
        return babyRepository.findAll();
    }

    public Optional<Baby> getBabyById(Integer babyId) {
        return babyRepository.findById(babyId);
    }

    public List<Baby> getBabyByUserId(Integer userId) {
        return babyRepository.findByUserId(userId);
    }

    public Baby createBaby(Baby baby) {
        return babyRepository.save(baby);
    }

    public Baby updateBaby(Integer babyId, Baby babyDetails) {
        Optional<Baby> baby = babyRepository.findById(babyId);
        if (baby.isPresent()) {
            Baby updatedBaby = baby.get();
            updatedBaby.setBabyName(babyDetails.getBabyName());
            updatedBaby.setBirth(babyDetails.getBirth());
            updatedBaby.setGender(babyDetails.getGender());
            updatedBaby.setUserId(babyDetails.getUserId());
            updatedBaby.setBabyPhotoId(babyDetails.getBabyPhotoId());
            return babyRepository.save(updatedBaby);
        }
        return null;
    }

    public void deleteBaby(Integer babyId) {
        babyRepository.deleteById(babyId);
    }
}