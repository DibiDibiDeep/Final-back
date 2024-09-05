package com.example.finalproj.memo.service;

import com.example.finalproj.memo.entity.Memo;
import com.example.finalproj.memo.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemoService {
    @Autowired
    private MemoRepository memoRepository;

    public List<Memo> getAllMemos() {
        return memoRepository.findAll();
    }

    public Optional<Memo> getMemoById(Integer id) {
        return memoRepository.findById(id);
    }

    public List<Memo> getMemosByUserId(Integer userId) {
        return memoRepository.findByUserId(userId);
    }

    public List<Memo> getMemosByTodayId(Integer todayId) {
        return memoRepository.findByTodayId(todayId);
    }

    public List<Memo> getMemosByCalendarId(Integer calendarId) {
        return memoRepository.findByCalendarId(calendarId);
    }

    public List<Memo> getMemosByCreatedAt(String createdAt) {
        return memoRepository.findByCreatedAt(createdAt);
    }

    public Memo createMemo(Memo memo) {
        return memoRepository.save(memo);
    }

    public Memo updateMemo(Integer id, Memo memoDetails) {
        Optional<Memo> memo = memoRepository.findById(id);
        if (memo.isPresent()) {
            Memo updatedMemo = memo.get();
            updatedMemo.setTodayId(memoDetails.getTodayId());
            updatedMemo.setUserId(memoDetails.getUserId());
            updatedMemo.setCalendarId(memoDetails.getCalendarId());
            updatedMemo.setCreatedAt(memoDetails.getCreatedAt());
            updatedMemo.setContent(memoDetails.getContent());
            return memoRepository.save(updatedMemo);
        }
        return null;
    }

    public void deleteMemo(Integer id) {
        memoRepository.deleteById(id);
    }
}