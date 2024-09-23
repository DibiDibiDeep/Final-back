package com.example.finalproj.user.service;

import com.example.finalproj.security.JwtTokenProvider;
import com.example.finalproj.user.entity.User;
import com.example.finalproj.user.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${google.client.id}")
    private String CLIENT_ID;

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 사용자 ID로 사용자 조회
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    // Google 사용자 인증
    public Map<String, Object> authenticateGoogleUser(String token) throws Exception {
        try {
            GoogleIdToken.Payload payload = verifyGoogleToken(token);
            String email = payload.getEmail();
            List<User> users = userRepository.findByEmail(email);

            Map<String, Object> response = new HashMap<>();
            User user;

            // 기존 사용자 확인 및 처리
            if (!users.isEmpty()) {
                user = users.get(0);  // 기존 사용자
                response.put("code", 200);
            } else {
                user = new User(email, (String) payload.get("name"));  // 새 사용자 생성
                user = userRepository.save(user);
                response.put("code", 201);
            }

            String jwtToken = jwtTokenProvider.generateToken(user.getUserId());
            response.put("user", user);
            response.put("token", jwtToken);
            return response;
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 400);
            errorResponse.put("message", "Authentication failed: " + e.getMessage());
            return errorResponse;
        }
    }

    // 사용자 정보 업데이트
    public User updateUser(Integer userId, User userDetails) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setName(userDetails.getName());
                    user.setEmail(userDetails.getEmail());
                    return userRepository.save(user);
                })
                .orElse(null);
    }

    // 사용자 삭제
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    // Google ID 토큰 검증
    private GoogleIdToken.Payload verifyGoogleToken(String token) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            return idToken.getPayload();
        } else {
            throw new Exception("Invalid ID token.");
        }
    }
}
