package com.example.finalproj.Chat.repository;

import com.example.finalproj.Chat.entity.ChatMessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessageDTO, Long> {


}
