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

    public List<AlimInf> getAllAlimInfs() {
        return alimInfRepository.findAll();
    }

    public Optional<AlimInf> getAlimInfById(Integer id) {
        return alimInfRepository.findById(id);
    }

    public AlimInf createAlimInf(AlimInf alimInf) {
        return alimInfRepository.save(alimInf);
    }

    public AlimInf updateAlimInf(Integer id, AlimInf alimInfDetails) {
        Optional<AlimInf> alimInf = alimInfRepository.findById(id);
        if (alimInf.isPresent()) {
            AlimInf existingAlimInf = alimInf.get();
            // Update fields
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

    public void deleteAlimInf(Integer id) {
        alimInfRepository.deleteById(id);
    }

    public List<AlimInf> getAlimInfsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return alimInfRepository.findByDateBetween(startDate, endDate);
    }
}