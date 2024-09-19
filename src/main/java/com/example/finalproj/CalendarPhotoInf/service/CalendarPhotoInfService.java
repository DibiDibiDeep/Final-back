package com.example.finalproj.CalendarPhotoInf.service;

import com.example.finalproj.CalendarPhotoInf.entity.CalendarPhotoInf;
import com.example.finalproj.CalendarPhotoInf.repository.CalendarPhotoInfRepository;
import com.example.finalproj.calendar.entity.Calendar;
import com.example.finalproj.calendar.repository.CalendarRepository;
import com.example.finalproj.baby.repository.BabyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class CalendarPhotoInfService {

    @Autowired
    private CalendarPhotoInfRepository calendarPhotoInfRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private BabyRepository babyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public CalendarPhotoInf saveAndProcessInferenceResult(Integer calendarPhotoId, String inferenceResult) {
        CalendarPhotoInf calendarPhotoInf = new CalendarPhotoInf();
        calendarPhotoInf.setCalendarPhotoId(calendarPhotoId);
        calendarPhotoInf.setInferenceResult(inferenceResult);
        calendarPhotoInf.setInferenceDate(LocalDateTime.now());

        try {
            Map<String, Object> resultMap = objectMapper.readValue(inferenceResult, Map.class);
            calendarPhotoInf.setUserId(Integer.parseInt((String) resultMap.get("user_id")));
            calendarPhotoInf.setBabyId(Integer.parseInt((String) resultMap.get("baby_id")));

            // Save the inference result
            calendarPhotoInf = calendarPhotoInfRepository.save(calendarPhotoInf);

            // Automatically process the inference result
            processCalendarEntries(calendarPhotoInf, resultMap);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error processing inference result", e);
        }

        return calendarPhotoInf;
    }

    private void processCalendarEntries(CalendarPhotoInf calendarPhotoInf, Map<String, Object> resultMap) {
        // year가 null인 경우 현재 년도를 사용
        String year = (String) resultMap.get("year");
        if (year == null) {
            year = String.valueOf(LocalDate.now().getYear());
        }

        String month = (String) resultMap.get("month");
        List<Map<String, Object>> events = (List<Map<String, Object>>) resultMap.get("events");

        for (Map<String, Object> event : events) {
            String date = (String) event.get("date");
            List<Map<String, Object>> activities = (List<Map<String, Object>>) event.get("activities");

            for (Map<String, Object> activity : activities) {
                Calendar calendar = createCalendarFromActivity(calendarPhotoInf, year, month, date, activity);
                saveCalendarIfNotExists(calendar);
            }
        }
    }

    private Calendar createCalendarFromActivity(CalendarPhotoInf calendarPhotoInf, String year, String month, String date, Map<String, Object> activity) {
        Calendar calendar = new Calendar();
        calendar.setUserId(calendarPhotoInf.getUserId());

        Integer babyId = calendarPhotoInf.getBabyId();
        if (babyId != null && babyRepository.existsById(babyId)) {
            calendar.setBabyId(babyId);
        }

        calendar.setCalendarPhotoId(calendarPhotoInf.getCalendarPhotoId());

        String paddedMonth = String.format("%02d", Integer.parseInt(month));
        String paddedDate = String.format("%02d", Integer.parseInt(date));

        LocalDate eventDate = LocalDate.parse(year + "-" + paddedMonth + "-" + paddedDate, DateTimeFormatter.ISO_DATE);
        calendar.setStartTime(eventDate.atStartOfDay());
        calendar.setEndTime(eventDate.atTime(23, 59, 59));

        String name = (String) activity.get("name");
        String information = (String) activity.get("infomation");  // "infomation" 오타 주의
        calendar.setTitle(name + (information != null && !information.isEmpty() ? " " + information : ""));

        String time = (String) activity.get("time");
        calendar.setLocation(time != null ? time : "");

        return calendar;
    }

    private void saveCalendarIfNotExists(Calendar calendar) {
        if (!calendarRepository.existsByUserIdAndStartTimeAndTitle(
                calendar.getUserId(), calendar.getStartTime(), calendar.getTitle())) {
            calendarRepository.save(calendar);
        }
    }

    public List<CalendarPhotoInf> getAllCalendarPhotoInfs() {
        return calendarPhotoInfRepository.findAllCalendarPhotoInfs();
    }
}