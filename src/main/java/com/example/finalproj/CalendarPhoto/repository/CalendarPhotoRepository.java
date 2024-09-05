package com.example.finalproj.CalendarPhoto.repository;

import com.example.finalproj.CalendarPhoto.entity.CalendarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CalendarPhotoRepository extends JpaRepository<CalendarPhoto, Integer> {
    List<CalendarPhoto> findByUserId(Integer userId);
    List<CalendarPhoto> findByBabyId(Integer babyId);
    List<CalendarPhoto> findByTakenAt(String takenAt);
}