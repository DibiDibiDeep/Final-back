package com.example.finalproj.ml.voiceML;

import org.springframework.context.ApplicationEvent;

public class VoiceMLProcessingCompletedEvent extends ApplicationEvent {
    private final String mlResponse;
    private final Integer voiceMemoId;

    public VoiceMLProcessingCompletedEvent(Object source, String mlResponse, Integer voiceMemoId) {
        super(source);
        this.mlResponse = mlResponse;
        this.voiceMemoId = voiceMemoId;
    }

    public String getMlResponse() {
        return mlResponse;
    }

    public Integer getVoiceMemoId() {
        return voiceMemoId;
    }
}