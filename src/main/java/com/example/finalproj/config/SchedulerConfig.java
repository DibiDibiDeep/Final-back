package com.example.finalproj.config;

import com.example.finalproj.ml.chatML.ChatMLService;
import com.example.finalproj.redis.service.RedisChatService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerConfig {

    private final RedisChatService redisChatService;
    private final ChatMLService chatMLService;

    public SchedulerConfig(RedisChatService redisChatService, ChatMLService chatMLService) {
        this.redisChatService = redisChatService;
        this.chatMLService = chatMLService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void resetChatHistories(Long userId, Long babyId) {
        chatMLService.resetChatHistory(userId, babyId);
    }
}
