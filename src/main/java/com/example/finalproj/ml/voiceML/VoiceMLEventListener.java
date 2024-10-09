package com.example.finalproj.ml.voiceML;

import com.example.finalproj.domain.memo.original.entity.Memo;
import com.example.finalproj.domain.memo.original.service.MemoService;
import com.example.finalproj.domain.memo.voice.entity.VoiceMemo;
import com.example.finalproj.domain.memo.voice.repository.VoiceMemoRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VoiceMLEventListener {

    private final MemoService memoService;
    private final VoiceMemoRepository voiceMemoRepository;

    public VoiceMLEventListener(MemoService memoService, VoiceMemoRepository voiceMemoRepository) {
        this.memoService = memoService;
        this.voiceMemoRepository = voiceMemoRepository;
    }

    @EventListener
    public void handleVoiceMLProcessingCompletedEvent(VoiceMLProcessingCompletedEvent event) {
        String mlResponse = event.getMlResponse();
        Integer voiceMemoId = event.getVoiceMemoId();

        VoiceMemo voiceMemo = voiceMemoRepository.findById(voiceMemoId).orElse(null);
        if (voiceMemo != null) {
            Memo memo = new Memo();
            memo.setUserId(voiceMemo.getUserId());
            memo.setBabyId(voiceMemo.getBabyId());
            memo.setDate(LocalDateTime.now());
            memo.setContent(mlResponse);
            memo.setSendToML(false);

            memoService.createMemo(memo);

            voiceMemo.setProcessed(true);
            voiceMemoRepository.save(voiceMemo);
        }
    }
}