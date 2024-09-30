package com.example.finalproj.TodaySum.service;

import com.example.finalproj.TodaySum.entity.TodaySum;
import com.example.finalproj.TodaySum.repository.TodaySumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodaySumService {

    private final TodaySumRepository todaySumRepository;

    @Autowired
    public TodaySumService(TodaySumRepository todaySumRepository) {
        this.todaySumRepository = todaySumRepository;
    }

    // 모든 TodaySum 레코드를 조회
    public List<TodaySum> getAllTodaySums() {
        return todaySumRepository.findAll();
    }

    // ID로 TodaySum 레코드 조회
    public Optional<TodaySum> getTodaySumById(Integer id) {
        return todaySumRepository.findById(id);
    }

    // TodaySum 레코드 생성
    public TodaySum createTodaySum(TodaySum todaySum) {
        return todaySumRepository.save(todaySum);
    }

    // TodaySum 레코드 업데이트
    public TodaySum updateTodaySum(Integer id, TodaySum todaySumDetails) {
        Optional<TodaySum> todaySum = todaySumRepository.findById(id);
        if (todaySum.isPresent()) {
            TodaySum existingTodaySum = todaySum.get();
            existingTodaySum.setUserId(todaySumDetails.getUserId());
            existingTodaySum.setBabyId(todaySumDetails.getBabyId());
            existingTodaySum.setContent(todaySumDetails.getContent());
            existingTodaySum.setDate(todaySumDetails.getDate());
            return todaySumRepository.save(existingTodaySum);
        }
        return null; // 레코드가 존재하지 않을 경우 null 반환
    }

    // TodaySum 레코드 삭제
    public void deleteTodaySum(Integer id) {
        todaySumRepository.deleteById(id);
    }

}
