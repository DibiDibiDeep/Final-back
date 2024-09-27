package com.example.finalproj.user.service;

import com.example.finalproj.user.entity.User;
import com.example.finalproj.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Value("${google.client.id}")
//    private String CLIENT_ID;
//
    public void createDummyUser() {
        // Check if the user already exists
        Optional<User> existingUser = findUserByIdAndEmail(1, "chulsoo@example.com");
        if (!existingUser.isPresent()) {
            User dummyUser = new User();
            dummyUser.setUserId(1);
            dummyUser.setEmail("chulsoo@example.com");
            dummyUser.setName("Dummy User");
            dummyUser.setNewUser(true); // Adjust as necessary
            // Save the dummy user to the database
            userRepository.save(dummyUser);
        }
    }

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 사용자 ID로 사용자 조회
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }
//
//    // Google 사용자 인증
//    public Map<String, Object> authenticateGoogleUser(String token) throws AuthenticationServiceException {
//        try {
//            GoogleIdToken.Payload payload = verifyGoogleToken(token);
//            String email = payload.getEmail();
//            String name = (String) payload.get("name");
//
//            User user = userRepository.findByEmail(email)
//                    .orElseGet(() -> {
//                        User newUser = new User(email, name);
//                        return userRepository.save(newUser);
//                    });
//
//            String jwtToken = jwtTokenProvider.generateToken(user.getUserId());
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("user", user);
//            response.put("token", jwtToken);
//            response.put("isNewUser", user.getCreatedAt().isAfter(LocalDateTime.now().minusSeconds(5)));
//
//            return response;
//        } catch (Exception e) {
//            throw new AuthenticationServiceException("Authentication failed: " + e.getMessage(), e);
//        }
//    }
//
//    // 개인정보 처리방침 메서드
//    public User acceptPrivacyPolicy(Integer userId) {
//        return userRepository.findById(userId).map(user -> {
//            user.setPrivacyPolicyAccepted(true);
//            return userRepository.save(user);
//        }).orElseThrow(() -> new RuntimeException("User not found"));
//    }

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

//    // Google ID 토큰 검증
//    private GoogleIdToken.Payload verifyGoogleToken(String token) throws Exception {
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
//                .setAudience(Collections.singletonList(CLIENT_ID))
//                .build();
//
//        GoogleIdToken idToken = verifier.verify(token);
//        if (idToken != null) {
//            return idToken.getPayload();
//        } else {
//            throw new AuthenticationServiceException("Invalid ID token.");
//        }
//    }

    // Find user by userId and email
    public Optional<User> findUserByIdAndEmail(Integer userId, String email) {
        return userRepository.findByUserIdAndEmail(userId, email);
    }

    // Check if user is new
    public boolean isNewUser(Optional<User> user) {
        return user.orElseThrow().isNewUser(); // Adjust logic as necessary
    }

}
