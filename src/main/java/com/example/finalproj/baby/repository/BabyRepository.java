package com.example.finalproj.baby.repository;

import com.example.finalproj.baby.entity.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BabyRepository extends JpaRepository<Baby, Integer> {
    List<Baby> findByUserId(Integer userId);
    boolean existsByUserId(Integer userId);
}