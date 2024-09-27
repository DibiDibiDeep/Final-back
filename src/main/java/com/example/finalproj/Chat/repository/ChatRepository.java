package com.example.finalproj.Chat.repository;

import com.example.finalproj.Chat.entity.ChatMessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessageDTO, Long> {


    List<ChatMessageDTO> findByUserIdAndBabyId(Long userId, Long babyId);
}
