package com.example.finalproj.baby.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Baby")
public class Baby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baby_id")
    private Integer babyId;

    @Column(name = "baby_name", nullable = false)
    private String babyName;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    // 기본 생성자
    public Baby() {}

    // 모든 필드를 포함한 생성자
    public Baby(Integer babyId, String babyName, LocalDate birth, String gender, Integer userId) {
        this.babyId = babyId;
        this.babyName = babyName;
        this.birth = birth;
        this.gender = gender;
        this.userId = userId;
    }

    // Getter와 Setter 메서드
    public Integer getBabyId() {
        return babyId;
    }

    public void setBabyId(Integer babyId) {
        this.babyId = babyId;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Baby{" +
                "babyId=" + babyId +
                ", babyName='" + babyName + '\'' +
                ", birth=" + birth +
                ", gender='" + gender + '\'' +
                ", userId=" + userId +
                '}';
    }
}