package com.example.finalproj.EventImg.repository;

import com.example.finalproj.EventImg.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
