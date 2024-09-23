package com.example.finalproj.ml.AlimML;

import com.example.finalproj.AlimInf.entity.AlimInf;
import com.example.finalproj.AlimInf.service.AlimInfService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AlimMLEventListener {

    private final RestTemplate restTemplate;
    private final String appUrl;
    private final AlimInfService alimInfService;

    // 생성자
    public AlimMLEventListener(RestTemplate restTemplate,
                               @Value("${app.url:http://localhost:8080}") String appUrl,
                               AlimInfService alimInfService) {
        this.restTemplate = restTemplate;
        this.appUrl = appUrl;
        this.alimInfService = alimInfService;
    }


    // Alim ML 처리 완료 이벤트 핸들러 (새로운 코드)
    @EventListener
    public void handleAlimMLProcessingCompletedEvent(AlimMLProcessingCompletedEvent event) {
        String mlResponse = event.getMlResponse();
        Integer alimId = event.getAlimId();

        try {
            AlimInf alimInf = AlimMLService.createAlimInfFromMLResponse(mlResponse, alimId);
            alimInfService.createAlimInf(alimInf);
            System.out.println("AlimInf created and saved successfully from event");
        } catch (Exception e) {
            System.err.println("Error processing Alim ML response from event: " + e.getMessage());
        }
    }
}