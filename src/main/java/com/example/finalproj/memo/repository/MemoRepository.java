package com.example.finalproj.memo.repository;

import com.example.finalproj.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Integer> {
    List<Memo> findByUserId(Integer userId);
    List<Memo> findByTodayId(Integer todayId);
    List<Memo> findByCalendarId(Integer calendarId);
    List<Memo> findByCreatedAt(String createdAt);
}