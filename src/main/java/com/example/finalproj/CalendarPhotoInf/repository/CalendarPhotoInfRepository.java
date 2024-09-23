package com.example.finalproj.CalendarPhotoInf.repository;

import com.example.finalproj.CalendarPhotoInf.entity.CalendarPhotoInf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalendarPhotoInfRepository extends JpaRepository<CalendarPhotoInf, Integer> {
    // 모든 CalendarPhotoInf 레코드를 조회하여 특정 필드만 포함된 객체 리스트 반환
    @Query("SELECT new com.example.finalproj.CalendarPhotoInf.entity.CalendarPhotoInf(cpi.calendarPhotoInfId, cpi.calendarPhotoId, cpi.inferenceResult, cpi.inferenceDate, cpi.userId, cpi.babyId) FROM CalendarPhotoInf cpi")
    List<CalendarPhotoInf> findAllCalendarPhotoInfs();
}
