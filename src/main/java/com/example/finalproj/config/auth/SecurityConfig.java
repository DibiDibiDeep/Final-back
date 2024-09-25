package toy.project.bulletin_board.config.auth;

import com.example.finalproj.config.auth.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

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
                        .requestMatchers("/", "/css/**", "images/**", "/js/**", "/login/*", "/logout/*", "/posts/**", "/comments/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(
                        (logoutConfig) -> logoutConfig.logoutSuccessUrl("/")
                )
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}