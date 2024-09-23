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
                // 모든 origin을 허용합니다.
                .allowedOrigins("*")
                // 허용할 HTTP 메소드를 지정합니다.
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 모든 헤더를 허용합니다.
                .allowedHeaders("*")
                // 자격 증명은 비활성화합니다 (allowedOrigins("*")와 함께 사용할 수 없음).
                .allowCredentials(false);
    }
}
