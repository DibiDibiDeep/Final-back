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

    @PostMapping("/google")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> tokenMap) {
        try {
            Map<String, Object> response = userService.authenticateGoogleUser(tokenMap.get("token"));
            User user = (User) response.get("user");

            if (user == null) {
                return ResponseEntity.badRequest().body("User authentication failed");
            }

            boolean hasBaby = babyService.userHasBaby(user.getUserId());

            Map<String, Object> finalResponse = new HashMap<>(response);
            finalResponse.put("user", user);  // Ensure user object is included
            finalResponse.put("hasBaby", hasBaby);

            return ResponseEntity.ok(finalResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userId, userDetails);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

//    // 테스트용 코드
//    @PostMapping("/test-create")
//    public ResponseEntity<?> createTestUser(@RequestBody User userDetails) {
//        try {
//            Map<String, Object> response = userService.createTestUser(userDetails);
//            return ResponseEntity.status((int) response.get("code")).body(response);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}