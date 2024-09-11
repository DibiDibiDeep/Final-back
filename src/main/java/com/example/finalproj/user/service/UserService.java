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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public Map<String, Object> authenticateGoogleUser(String token) throws Exception {
        try {
            GoogleIdToken.Payload payload = verifyGoogleToken(token);
            String email = payload.getEmail();
            List<User> users = userRepository.findByEmail(email);

        Map<String, Object> response = new HashMap<>();
        User user;

            if (!users.isEmpty()) {
                user = users.get(0);  // Get the first user if exists
                response.put("code", 200);
            } else {
                user = new User(email, (String) payload.get("name"));
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

    public User updateUser(Integer userId, User userDetails) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setName(userDetails.getName());
                    user.setEmail(userDetails.getEmail());
                    return userRepository.save(user);
                })
                .orElse(null);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

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

    // 테스트용 코드
//    public Map<String, Object> createTestUser(User userDetails) {
//        User user = userRepository.findByEmail(userDetails.getEmail());
//
//        Map<String, Object> response = new HashMap<>();
//        if (user == null) {
//            user = new User(userDetails.getEmail(), userDetails.getName());
//            user = userRepository.save(user);
//            response.put("code", 201);
//        } else {
//            response.put("code", 200);
//        }
//        String jwtToken = jwtTokenProvider.generateToken(user.getUserId());
//        response.put("user", user);
//        response.put("token", jwtToken);
//        return response;
//    }
}