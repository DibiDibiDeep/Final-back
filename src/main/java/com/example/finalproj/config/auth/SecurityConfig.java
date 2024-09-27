package com.example.finalproj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정 활성화
                .cors().and()
                // CSRF 비활성화
                .csrf().disable()
                // 세션 관리 정책을 Stateless로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 모든 요청에 대해 인증 필요 없음
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // 모든 요청을 허용
                );

        return http.build();
    }
}
