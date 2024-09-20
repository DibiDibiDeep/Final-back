package com.example.finalproj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 CORS 매핑을 추가합니다.
        registry.addMapping("/**")
                // 허용할 원본 URL을 지정합니다. 배포 시 도메인으로 변경할 필요가 있습니다.
                .allowedOrigins("${NEXT_PUBLIC_FRONTEND_API_URL}", "http://localhost:3000", "http://frontend:3000", "http://192.168.0.227:3000") //TODO: 배포시 ip주소 도메인으로 변경 필요
                // 허용할 HTTP 메소드를 지정합니다.
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 모든 헤더를 허용합니다.
                .allowedHeaders("*")
                // 자격 증명을 허용합니다.
                .allowCredentials(true);
    }
}