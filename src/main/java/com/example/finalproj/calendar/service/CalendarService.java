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

    public Calendar updateCalendar(Integer id, Calendar calendarDetails) {
        Optional<Calendar> calendar = calendarRepository.findById(id);
        if (calendar.isPresent()) {
            Calendar updatedCalendar = calendar.get();
            updatedCalendar.setUserId(calendarDetails.getUserId());
            updatedCalendar.setCalendarPhotoId(calendarDetails.getCalendarPhotoId());
            updatedCalendar.setBabyId(calendarDetails.getBabyId());
            updatedCalendar.setTitle(calendarDetails.getTitle());
            updatedCalendar.setDescription(calendarDetails.getDescription());
            updatedCalendar.setDate(calendarDetails.getDate());
            updatedCalendar.setLocation(calendarDetails.getLocation());
            return calendarRepository.save(updatedCalendar);
        }
        return null;
    }

    public void deleteCalendar(Integer id) {
        calendarRepository.deleteById(id);
    }
}