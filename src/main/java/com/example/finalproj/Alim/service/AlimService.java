package com.example.finalproj.Alim.service;

import com.example.finalproj.Alim.entity.Alim;
import com.example.finalproj.Alim.repository.AlimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlimService {
    @Autowired
    private AlimRepository alimRepository;

    public List<Alim> getAlimsByDate(LocalDate date) {
        return alimRepository.findByDate(date);
    }

    public Alim createAlim(Alim alim) {
        return alimRepository.save(alim);
    }

    public Optional<Alim> getAlimById(Integer id) {
        return alimRepository.findById(id);
    }

    public List<Alim> getAllAlims() {
        return alimRepository.findAll();
    }

    public List<Alim> getAlimsByUserIdAndDateRange(Integer userId, LocalDateTime start, LocalDateTime end) {
        return alimRepository.findByUserIdAndDateBetween(userId, start, end);
    }

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

    public void deleteAlim(Integer id) {
        alimRepository.deleteById(id);
    }
}