package com.example.finalproj.Alim.repository;

import com.example.finalproj.Alim.entity.Alim;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlimRepository extends JpaRepository<Alim, Integer> {
    List<Alim> findByBabyId(Integer babyId);
    List<Alim> findByCreatedAt(String createdAt);
}