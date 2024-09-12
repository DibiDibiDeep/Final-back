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

    public List<TodaySum> getAllTodaySums() {
        return todaySumRepository.findAll();
    }

    public Optional<TodaySum> getTodaySumById(Integer id) {
        return todaySumRepository.findById(id);
    }

    public TodaySum createTodaySum(TodaySum todaySum) {
        return todaySumRepository.save(todaySum);
    }

    public TodaySum updateTodaySum(Integer id, TodaySum todaySumDetails) {
        Optional<TodaySum> todaySum = todaySumRepository.findById(id);
        if (todaySum.isPresent()) {
            TodaySum existingTodaySum = todaySum.get();
            existingTodaySum.setUserId(todaySumDetails.getUserId());
            existingTodaySum.setBookId(todaySumDetails.getBookId());
            existingTodaySum.setContent(todaySumDetails.getContent());
            existingTodaySum.setStartDate(todaySumDetails.getStartDate());
            existingTodaySum.setEndDate(todaySumDetails.getEndDate());
            existingTodaySum.setGeneratedDate(todaySumDetails.getGeneratedDate());
            existingTodaySum.setRevisionDate(todaySumDetails.getRevisionDate());
            return todaySumRepository.save(existingTodaySum);
        }
        return null;
    }

    public void deleteTodaySum(Integer id) {
        todaySumRepository.deleteById(id);
    }

    public List<TodaySum> getTodaySumsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return todaySumRepository.findByDateRange(startDate, endDate);
    }
}