package com.example.finalproj.calendar.service;

import com.example.finalproj.calendar.entity.Calendar;
import com.example.finalproj.calendar.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarService {
    @Autowired
    private CalendarRepository calendarRepository;


    public Calendar getCalendarById(Integer calendarId) {
        Optional<Calendar> calendar = calendarRepository.findById(calendarId);
        return calendar.orElse(null);
    }

    public List<Calendar> getCalendarsByUserId(Integer userId) {
        return calendarRepository.findByUserId(userId);
    }

    public List<Calendar> getCalendarsByBabyId(Integer babyId) {
        return calendarRepository.findByBabyId(babyId);
    }

    public List<Calendar> getCalendarsByDate(String date) {
        return calendarRepository.findByDate(date);
    }

    public Calendar createCalendar(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    public Calendar updateCalendar(Integer calendarId, Calendar calendarDetails) {
        Optional<Calendar> calendar = calendarRepository.findById(calendarId);

        if (calendar.isPresent()) {
            Calendar updatedCalendar = calendar.get();
            updatedCalendar.setUserId(calendarDetails.getUserId());
            updatedCalendar.setBabyId(calendarDetails.getBabyId());
            updatedCalendar.setCalendarPhotoId(calendarDetails.getCalendarPhotoId());
            updatedCalendar.setTodayId(calendarDetails.getTodayId());
            updatedCalendar.setFairyTaleId(calendarDetails.getFairyTaleId());
            updatedCalendar.setTitle(calendarDetails.getTitle());
            updatedCalendar.setStartTime(calendarDetails.getStartTime());
            updatedCalendar.setEndTime(calendarDetails.getEndTime());
            updatedCalendar.setLocation(calendarDetails.getLocation());
            return calendarRepository.save(updatedCalendar);
        }
        return null;
    }

    public void deleteCalendar(Integer calendarId) {
        calendarRepository.deleteById(calendarId);

    }
}