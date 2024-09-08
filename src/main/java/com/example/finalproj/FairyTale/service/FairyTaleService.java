package com.example.finalproj.FairyTale.service;

import com.example.finalproj.FairyTale.entity.FairyTale;
import com.example.finalproj.FairyTale.repository.FairyTaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FairyTaleService {

    private final FairyTaleRepository fairyTaleRepository;

    @Autowired
    public FairyTaleService(FairyTaleRepository fairyTaleRepository) {
        this.fairyTaleRepository = fairyTaleRepository;
    }

    public List<FairyTale> getAllFairyTales() {
        return fairyTaleRepository.findAll();
    }

    public Optional<FairyTale> getFairyTaleById(Integer id) {
        return fairyTaleRepository.findById(id);
    }

    public FairyTale createFairyTale(FairyTale fairyTale) {
        return fairyTaleRepository.save(fairyTale);
    }

    public FairyTale updateFairyTale(Integer id, FairyTale fairyTaleDetails) {
        Optional<FairyTale> fairyTale = fairyTaleRepository.findById(id);
        if (fairyTale.isPresent()) {
            FairyTale existingFairyTale = fairyTale.get();
            existingFairyTale.setUserId(fairyTaleDetails.getUserId());
            existingFairyTale.setContent(fairyTaleDetails.getContent());
            existingFairyTale.setStartDate(fairyTaleDetails.getStartDate());
            existingFairyTale.setEndDate(fairyTaleDetails.getEndDate());
            existingFairyTale.setGeneratedDate(fairyTaleDetails.getGeneratedDate());
            return fairyTaleRepository.save(existingFairyTale);
        }
        return null;
    }

    public void deleteFairyTale(Integer id) {
        fairyTaleRepository.deleteById(id);
    }
}