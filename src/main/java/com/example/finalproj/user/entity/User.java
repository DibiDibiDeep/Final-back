package com.example.finalproj.user.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "`User`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private boolean newUser;

    // Default constructor
    public User() {}

    public User(Integer userId, String email, String name, LocalDateTime createdAt, boolean newUser) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.newUser = newUser;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", newUser=" + newUser +
                '}';
    }

    public User update(String name) {
        this.name = name;
        return this;
    }
}