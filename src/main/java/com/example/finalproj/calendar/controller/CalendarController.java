package com.example.finalproj.calendar.controller;

import com.example.finalproj.calendar.entity.Calendar;
import com.example.finalproj.calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/calendars")
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    @GetMapping("/{calendarId}")
    public ResponseEntity<Calendar> getCalendarById(@PathVariable Integer calendarId) {
        Calendar calendar = calendarService.getCalendarById(calendarId);
        if (calendar == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(calendar);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Calendar>> getCalendarsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(calendarService.getCalendarsByUserId(userId));
    }

    @GetMapping("/baby/{babyId}")
    public ResponseEntity<List<Calendar>> getCalendarsByBabyId(@PathVariable Integer babyId) {
        return ResponseEntity.ok(calendarService.getCalendarsByBabyId(babyId));
    }

    @GetMapping
    public ResponseEntity<List<Calendar>> getCalendarsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Calendar> calendars = calendarService.getCalendarsByDate(date);
        return ResponseEntity.ok(calendars);
    }

    @GetMapping("/date-range")
    public List<Calendar> getCalendarsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return calendarService.getCalendarsByDateRange(startDate, endDate);
    }

    @PostMapping
    public ResponseEntity<Calendar> createCalendar(@RequestBody Calendar calendar) {
        return ResponseEntity.ok(calendarService.createCalendar(calendar));
    }


    @PutMapping("/{calendarId}")
    public ResponseEntity<Calendar> updateCalendar(@PathVariable Integer calendarId, @RequestBody Calendar calendarDetails) {
        Calendar updatedCalendar = calendarService.updateCalendar(calendarId, calendarDetails);
        if (updatedCalendar == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCalendar);
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity<Void> deleteCalendar(@PathVariable Integer calendarId) {
        calendarService.deleteCalendar(calendarId);

        return ResponseEntity.ok().build();
    }
}