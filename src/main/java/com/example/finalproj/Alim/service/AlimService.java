package com.example.finalproj.Alim.service;

import com.example.finalproj.Alim.entity.Alim;
import com.example.finalproj.Alim.repository.AlimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AlimService {
    @Autowired
    private AlimRepository alimRepository;

    public List<Alim> getAllAlims() {
        return alimRepository.findAll();
    }

    public Optional<Alim> getAlimById(Integer id) {
        return alimRepository.findById(id);
    }

    public List<Alim> getAlimsByBabyId(Integer babyId) {
        return alimRepository.findByBabyId(babyId);
    }

    public List<Alim> getAlimsByCreatedAt(String createdAt) {
        return alimRepository.findByCreatedAt(createdAt);
    }

    public Alim createAlim(Alim alim) {
        return alimRepository.save(alim);
    }

    public Alim updateAlim(Integer id, Alim alimDetails) {
        Optional<Alim> alim = alimRepository.findById(id);
        if (alim.isPresent()) {
            Alim updatedAlim = alim.get();
            updatedAlim.setBabyId(alimDetails.getBabyId());
            updatedAlim.setContent(alimDetails.getContent());
            updatedAlim.setCreatedAt(alimDetails.getCreatedAt());
            return alimRepository.save(updatedAlim);
        }
        return null;
    }

    public void deleteAlim(Integer id) {
        alimRepository.deleteById(id);
    }
}