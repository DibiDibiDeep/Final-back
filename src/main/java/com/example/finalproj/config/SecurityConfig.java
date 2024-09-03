//package com.example.finalproj.config;
//
//import com.example.finalproj.config.handler.AuthSuccessHandler;
//import com.example.finalproj.config.handler.AuthFailHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private AuthFailHandler authFailHandler;
//
//    @Autowired
//    private AuthSuccessHandler authSuccessHandler;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                .requestMatchers("/fonts/**","/img/**","/css/**","/images/**","/styles/**");
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/login/admin/regist", "/", "/main", "/login/auth/*", "/api/**").permitAll()
//                        .requestMatchers("/main").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .usernameParameter("user")
//                        .passwordParameter("pass")
//                        .defaultSuccessUrl("/main", true)
//                        .successHandler(authSuccessHandler)
//                        .failureHandler(authFailHandler)
//                )
//                .logout(logout -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
//                        .deleteCookies("JSESSIONID")
//                        .invalidateHttpSession(true)
//                        .logoutSuccessUrl("/")
//                )
//                .sessionManagement(session -> session
//                        .maximumSessions(1)
//                        .expiredUrl("/")
//                )
//                .csrf(csrf -> csrf.disable());
//
//        return http.build();
//    }
//}