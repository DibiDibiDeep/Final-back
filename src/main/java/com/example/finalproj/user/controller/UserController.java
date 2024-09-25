package com.example.finalproj.user.controller;

import com.example.finalproj.baby.service.BabyService;
import com.example.finalproj.user.entity.User;
import com.example.finalproj.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final BabyService babyService;

    @Autowired
    public UserController(UserService userService, BabyService babyService) {
        this.userService = userService;
        this.babyService = babyService;
    }
    
    // 자체 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String userIdString = loginData.get("userId");
        String userEmail = loginData.get("email");

        logger.info("Attempting to log in user with ID: {} and Email: {}", userIdString, userEmail);

        try {
            Integer userId = Integer.valueOf(userIdString);
            Optional<User> optionalUser = userService.findUserByIdAndEmail(userId, userEmail);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                boolean isNewUser = user.isNewUser();

                Map<String, Object> response = new HashMap<>();
                response.put("redirectUrl", isNewUser ? "/initialSetting" : "/home");
                response.put("user", user);

                logger.info("User logged in successfully: {}", userId);
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Login failed: User not found for ID: {} and Email: {}", userId, userEmail);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user ID or email.");
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid user ID format: {}", userIdString);
            return ResponseEntity.badRequest().body("User ID must be a valid number.");
        } catch (Exception e) {
            logger.error("Error during login attempt", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login.");
        }
    }

    // Google 사용자 인증
//    @PostMapping("/google")
//    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> tokenMap) {
//        logger.info("Attempting to authenticate Google user");
//        try {
//            String token = tokenMap.get("token");
//            if (token == null || token.isEmpty()) {
//                logger.warn("Received empty token for Google authentication");
//                return ResponseEntity.badRequest().body("Token is required");
//            }
//
//            Map<String, Object> response = userService.authenticateGoogleUser(token);
//            User user = (User) response.get("user");
//            if (user == null) {
//                logger.warn("User authentication failed");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User authentication failed");
//            }
//
//            boolean hasBaby = babyService.userHasBaby(user.getUserId());
//
//            Map<String, Object> finalResponse = new HashMap<>(response);
//            finalResponse.put("user", user);
//            finalResponse.put("hasBaby", hasBaby);
//
//            logger.info("User successfully authenticated: {}", user.getUserId());
//            return ResponseEntity.ok(finalResponse);
//        } catch (Exception e) {
//            logger.error("Error during Google authentication", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred during authentication: " + e.getMessage());
//        }
//    }

//    @PostMapping("/dev-login")
//    public ResponseEntity<?> devLogin() {
//        logger.info("Development login endpoint called");
//        try {
//            // 더미 데이터 생성
//            User dummyUser = new User();
//            dummyUser.setUserId(5);
//            dummyUser.setEmail("dummy@example.com");
//            dummyUser.setName("Dummy User");
//
//            // 더미 액세스 토큰 생성
//            String accessToken = "dummy_access_token_" + System.currentTimeMillis();
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("user", dummyUser);
//            response.put("hasBaby", true);
//            response.put("babyId", 6);
//            response.put("accessToken", accessToken);
//
//            logger.info("Dummy user created with ID: {}", dummyUser.getUserId());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            logger.error("Error in development login", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred during development login: " + e.getMessage());
//        }
//    }

//    @PostMapping("/{userId}/accept-privacy-policy")
//    public ResponseEntity<?> acceptPrivacyPolicy(@PathVariable Integer userId) {
//        try {
//            User updatedUser = userService.acceptPrivacyPolicy(userId);
//            return ResponseEntity.ok(updatedUser);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    // 사용자 ID로 사용자 조회
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
        logger.info("Fetching user with ID: {}", userId);
        try {
            return userService.getUserById(userId)
                    .map(user -> {
                        logger.info("User found: {}", userId);
                        return ResponseEntity.ok(user);
                    })
                    .orElseGet(() -> {
                        logger.warn("User not found: {}", userId);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            logger.error("Error fetching user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the user");
        }
    }

    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        logger.info("Fetching all users");
        try {
            List<User> users = userService.getAllUsers();
            logger.info("Fetched {} users", users.size());
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching all users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching users");
        }
    }

    // 사용자 정보 업데이트
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody User userDetails) {
        logger.info("Updating user with ID: {}", userId);
        try {
            User updatedUser = userService.updateUser(userId, userDetails);
            if (updatedUser == null) {
                logger.warn("User not found for update: {}", userId);
                return ResponseEntity.notFound().build();
            }
            logger.info("User updated successfully: {}", userId);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            logger.error("Error updating user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the user");
        }
    }

    // 사용자 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        logger.info("Deleting user with ID: {}", userId);
        try {
            userService.deleteUser(userId);
            logger.info("User deleted successfully: {}", userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the user");
        }
    }
}
