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
        GoogleIdToken.Payload payload = verifyGoogleToken(token);

        String email = payload.getEmail();
        User user = userRepository.findByEmail(email);

        Map<String, Object> response = new HashMap<>();
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName((String) payload.get("name"));
            user = userRepository.save(user);
            response.put("user", user);
            response.put("code", 201);
        } else {
            String jwtToken = jwtTokenProvider.generateToken(user.getUserId());
            response.put("user", user);
            response.put("token", jwtToken);
            response.put("code", 200);
        }
        return response;
    }

    public User updateUser(Integer userId, User userDetails) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setName(userDetails.getName());
            updatedUser.setEmail(userDetails.getEmail());
            return userRepository.save(updatedUser);
        }
        return null;
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
}