package com.example.finalproj.user.controller;

import com.example.finalproj.baby.service.BabyService;
import com.example.finalproj.user.entity.User;
import com.example.finalproj.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BabyService babyService;

    // Google 사용자 인증
    @PostMapping("/google")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> tokenMap) {
        try {
            Map<String, Object> response = userService.authenticateGoogleUser(tokenMap.get("token"));
            User user = (User) response.get("user");

            if (user == null) {
                return ResponseEntity.badRequest().body("User authentication failed");
            }

            // 사용자가 아기를 가지고 있는지 확인
            boolean hasBaby = babyService.userHasBaby(user.getUserId());

            // 최종 응답 객체 생성
            Map<String, Object> finalResponse = new HashMap<>(response);
            finalResponse.put("user", user);  // 사용자 객체 포함
            finalResponse.put("hasBaby", hasBaby);

            return ResponseEntity.ok(finalResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 사용자 ID로 사용자 조회
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 사용자 정보 업데이트
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userId, userDetails);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    // 사용자 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
