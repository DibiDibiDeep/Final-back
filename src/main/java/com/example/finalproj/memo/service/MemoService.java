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

    public Memo createMemo(Memo memo) {
        return memoRepository.save(memo);
    }

    public Optional<Memo> getMemoById(Integer id) {
        return memoRepository.findById(id);
    }

    public List<Memo> getAllMemos() {
        return memoRepository.findAll();
    }

    public List<Memo> getMemosByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return memoRepository.findByDateBetween(startDate, endDate);
    }

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

    public void deleteMemo(Integer id) {
        memoRepository.deleteById(id);
    }

    public List<Memo> getMemosByDate(LocalDate date) {
        return memoRepository.findByDate(date);
    }

}