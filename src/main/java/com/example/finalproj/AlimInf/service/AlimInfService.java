package com.example.finalproj.AlimInf.service;

import com.example.finalproj.AlimInf.entity.AlimInf;
import com.example.finalproj.AlimInf.repository.AlimInfRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AlimInfService {

    private final AlimInfRepository alimInfRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AlimInfService(AlimInfRepository alimInfRepository, ObjectMapper objectMapper) {
        this.alimInfRepository = alimInfRepository;
        this.objectMapper = objectMapper;
    }

    public AlimInf createAlimInf(Map<String, Object> alimInfData) {
        AlimInf alimInf = new AlimInf();
        Integer maxAlimId = alimInfRepository.findMaxAlimId();
        alimInf.setAlimId(maxAlimId + 1);

        // 엔티티의 나머지 필드들도 필요에 따라 설정
        alimInf.setDate(LocalDateTime.now());

        // Set fields from map
        setFieldFromMap(alimInfData, "alim_id", alimInf, "setAlimId", Integer.class);
        setFieldFromMap(alimInfData, "user_id", alimInf, "setUserId", Integer.class);
        setFieldFromMap(alimInfData, "baby_id", alimInf, "setBabyId", Integer.class);
        setFieldFromMap(alimInfData, "today_id", alimInf, "setTodayId", Integer.class);
        setFieldFromMap(alimInfData, "name", alimInf, "setName", String.class);
        setFieldFromMap(alimInfData, "emotion", alimInf, "setEmotion", String.class);
        setFieldFromMap(alimInfData, "health", alimInf, "setHealth", String.class);
        setFieldFromMap(alimInfData, "nutrition", alimInf, "setNutrition", String.class);
        setFieldFromMap(alimInfData, "social", alimInf, "setSocial", String.class);
        setFieldFromMap(alimInfData, "special", alimInf, "setSpecial", String.class);
        setFieldFromMap(alimInfData, "diary", alimInf, "setDiary", String.class);
        setFieldFromMap(alimInfData, "role", alimInf, "setRole", String.class);
        if (alimInfData.containsKey("date") && alimInfData.get("date") != null) {
            String dateStr = alimInfData.get("date").toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
            alimInf.setDate(dateTime);
        } else {
            LocalDateTime currentDateTime = LocalDateTime.now();
            alimInf.setDate(currentDateTime);
        }
        // Handle activities and keywords
        handleListField(alimInfData, "activities", alimInf, "setActivities");
        handleListField(alimInfData, "keywords", alimInf, "setKeywords");

        return alimInfRepository.save(alimInf);
    }

    private void setFieldFromMap(Map<String, Object> map, String key, AlimInf alimInf, String setterName, Class<?> type) {
        if (map.containsKey(key)) {
            Object value = map.get(key);
            try {
                if (type.isInstance(value)) {
                    alimInf.getClass().getMethod(setterName, type).invoke(alimInf, value);
                } else if (value instanceof String && type == Integer.class) {
                    alimInf.getClass().getMethod(setterName, Integer.class).invoke(alimInf, Integer.parseInt((String) value));
                }
            } catch (Exception e) {
                throw new RuntimeException("Error setting field " + key, e);
            }
        }
    }

    private void handleListField(Map<String, Object> map, String key, AlimInf alimInf, String setterName) {
        if (map.containsKey(key)) {
            Object value = map.get(key);
            try {
                if (value instanceof List) {
                    List<?> list = (List<?>) value;
                    String joinedString = String.join(", ", list.stream().map(Object::toString).toArray(String[]::new));
                    alimInf.getClass().getMethod(setterName, String.class).invoke(alimInf, joinedString);
                } else if (value instanceof String) {
                    alimInf.getClass().getMethod(setterName, String.class).invoke(alimInf, (String) value);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error setting list field " + key, e);
            }
        }
    }

    public AlimInf updateAlimInf(Integer id, Map<String, Object> alimInfData) {
        AlimInf existingAlimInf = alimInfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AlimInf not found"));

        // Update fields
        setFieldFromMap(alimInfData, "alim_id", existingAlimInf, "setAlimId", Integer.class);
        setFieldFromMap(alimInfData, "user_id", existingAlimInf, "setUserId", Integer.class);
        setFieldFromMap(alimInfData, "baby_id", existingAlimInf, "setBabyId", Integer.class);
        setFieldFromMap(alimInfData, "today_id", existingAlimInf, "setTodayId", Integer.class);
        setFieldFromMap(alimInfData, "name", existingAlimInf, "setName", String.class);
        setFieldFromMap(alimInfData, "emotion", existingAlimInf, "setEmotion", String.class);
        setFieldFromMap(alimInfData, "health", existingAlimInf, "setHealth", String.class);
        setFieldFromMap(alimInfData, "nutrition", existingAlimInf, "setNutrition", String.class);
        setFieldFromMap(alimInfData, "social", existingAlimInf, "setSocial", String.class);
        setFieldFromMap(alimInfData, "special", existingAlimInf, "setSpecial", String.class);
        setFieldFromMap(alimInfData, "diary", existingAlimInf, "setDiary", String.class);
        setFieldFromMap(alimInfData, "role", existingAlimInf, "setRole", String.class);

        // Handle activities and keywords
        handleListField(alimInfData, "activities", existingAlimInf, "setActivities");
        handleListField(alimInfData, "keywords", existingAlimInf, "setKeywords");

        return alimInfRepository.save(existingAlimInf);
    }

    // 모든 AlimInf 레코드를 조회
    public List<AlimInf> getAllAlimInfs() {
        return alimInfRepository.findAll();
    }

    // ID로 특정 AlimInf 레코드를 조회
    public Optional<AlimInf> getAlimInfById(Integer id) {
        return alimInfRepository.findById(id);
    }

    // 특정 AlimInf 레코드를 삭제
    public void deleteAlimInf(Integer id) {
        alimInfRepository.deleteById(id);
    }

    // 특정 날짜 범위 내의 AlimInf 레코드를 조회
    public List<AlimInf> getAlimInfsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return alimInfRepository.findByDateBetween(startDate, endDate);
    }

    public Optional<AlimInf> getAlimInfByAlimId(Integer alimId) {
        return alimInfRepository.findByAlimId(alimId);
    }
}
