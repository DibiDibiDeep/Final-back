package com.example.finalproj.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        (csrfConfig) -> csrfConfig.disable()
                )
                .headers(
                        (headerConfig) -> headerConfig.frameOptions(
                                frameOptionsConfig -> frameOptionsConfig.disable()
                        )
                )
                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest
                        .requestMatchers("/", "/css/**", "images/**", "/js/**", "/login/*", "/logout/*", "/api/auth/**", "/api/books/**", "/api/**", "api/auth/login", "/api/alim-inf","/api/alim-inf/**", "/api/books/generate_fairytale","/api/baby-photos/**","/api/baby-photos/baby/", "/api/books/user/**", "/api/alim-inf/alim-id/**","/api/chat/send/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(
                        (logoutConfig) -> logoutConfig.logoutSuccessUrl("/")
                )
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}