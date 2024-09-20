package com.example.finalproj.memo.service;

import com.example.finalproj.memo.entity.Memo;
import com.example.finalproj.memo.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemoService {

    @Autowired
    private MemoRepository memoRepository;

    // 새 메모 생성
    public Memo createMemo(Memo memo) {
        return memoRepository.save(memo);
    }

    // ID로 메모 조회
    public Optional<Memo> getMemoById(Integer id) {
        return memoRepository.findById(id);
    }

    // 모든 메모 조회
    public List<Memo> getAllMemos() {
        return memoRepository.findAll();
    }

    // 날짜 범위로 메모 조회
    public List<Memo> getMemosByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return memoRepository.findByDateBetween(startDate, endDate);
    }

    // 메모 수정
    public Memo updateMemo(Integer id, Memo memoDetails) {
        Optional<Memo> memo = memoRepository.findById(id);
        if (memo.isPresent()) {
            Memo existingMemo = memo.get();
            existingMemo.setUserId(memoDetails.getUserId());
            existingMemo.setTodayId(memoDetails.getTodayId());
            existingMemo.setBookId(memoDetails.getBookId());
            existingMemo.setDate(memoDetails.getDate());
            existingMemo.setContent(memoDetails.getContent());
            return memoRepository.save(existingMemo);
        }
        return null;
    }

    // 메모 삭제
    public void deleteMemo(Integer id) {
        memoRepository.deleteById(id);
    }

    // 특정 날짜의 메모 조회
    public List<Memo> getMemosByDate(LocalDate date) {
        return memoRepository.findByDate(date);
    }

    // 특정 사용자의 특정 날짜 메모 조회
    public List<Memo> getMemosByUserAndDate(Integer userId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return memoRepository.findByUserIdAndDateBetween(userId, startOfDay, endOfDay);
    }
}