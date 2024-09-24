package com.example.finalproj.AlimInf.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Alim_inf")
public class AlimInf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aliminf_id")
    private Integer alimInfId;

    @Column(name = "alim_id")
    private Integer alimId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "baby_id")
    private Integer babyId;

    @Column(name = "today_id")
    private Integer todayId;

    private String name;
    @Column(name = "age", nullable = true)
    private Integer age;
    private String gender;
    private String emotion;
    private String health;
    private String nutrition;

    @Column(columnDefinition = "TEXT")
    private String activities;

    private String social;
    private String special;

    @Column(columnDefinition = "TEXT")
    private String keywords;

    @Column(columnDefinition = "TEXT")
    private String diary;

    @Column(name = "date")
    private LocalDateTime date;

    public AlimInf() {
    }

    public AlimInf(Integer alimInfId, Integer alimId, Integer userId, Integer babyId, Integer todayId, String name, Integer age, String gender, String emotion, String health, String nutrition, String activities, String social, String special, String keywords, String diary, LocalDateTime date) {
        this.alimInfId = alimInfId;
        this.alimId = alimId;
        this.userId = userId;
        this.babyId = babyId;
        this.todayId = todayId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.emotion = emotion;
        this.health = health;
        this.nutrition = nutrition;
        this.activities = activities;
        this.social = social;
        this.special = special;
        this.keywords = keywords;
        this.diary = diary;
        this.date = date;
    }

    public Integer getAlimInfId() {
        return alimInfId;
    }

    public void setAlimInfId(Integer alimInfId) {
        this.alimInfId = alimInfId;
    }

    public Integer getAlimId() {
        return alimId;
    }

    public void setAlimId(Integer alimId) {
        this.alimId = alimId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBabyId() {
        return babyId;
    }

    public void setBabyId(Integer babyId) {
        this.babyId = babyId;
    }

    public Integer getTodayId() {
        return todayId;
    }

    public void setTodayId(Integer todayId) {
        this.todayId = todayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AlimInf{" +
                "alimInfId=" + alimInfId +
                ", alimId=" + alimId +
                ", userId=" + userId +
                ", babyId=" + babyId +
                ", todayId=" + todayId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", emotion='" + emotion + '\'' +
                ", health='" + health + '\'' +
                ", nutrition='" + nutrition + '\'' +
                ", activities='" + activities + '\'' +
                ", social='" + social + '\'' +
                ", special='" + special + '\'' +
                ", keywords='" + keywords + '\'' +
                ", diary='" + diary + '\'' +
                ", date=" + date +
                '}';
    }
}
