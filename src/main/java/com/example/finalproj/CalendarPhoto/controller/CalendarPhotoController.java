package com.example.finalproj.CalendarPhoto.controller;

import com.example.finalproj.CalendarPhoto.entity.CalendarPhoto;
import com.example.finalproj.CalendarPhoto.service.CalendarPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/calendar-photos")
public class CalendarPhotoController {

    @Autowired
    private CalendarPhotoService calendarPhotoService;

    @GetMapping
    public ResponseEntity<List<CalendarPhoto>> getAllCalendarPhotos() {
        return ResponseEntity.ok(calendarPhotoService.getAllCalendarPhotos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarPhoto> getCalendarPhotoById(@PathVariable Integer id) {
        return calendarPhotoService.getCalendarPhotoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CalendarPhoto>> getCalendarPhotosByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(calendarPhotoService.getCalendarPhotosByUserId(userId));
    }

    @GetMapping("/baby/{babyId}")
    public ResponseEntity<List<CalendarPhoto>> getCalendarPhotosByBabyId(@PathVariable Integer babyId) {
        return ResponseEntity.ok(calendarPhotoService.getCalendarPhotosByBabyId(babyId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<CalendarPhoto>> getCalendarPhotosByTakenAt(@PathVariable LocalDateTime date) {
        return ResponseEntity.ok(calendarPhotoService.getCalendarPhotosByDate(date));
    }

    @PostMapping
    public ResponseEntity<CalendarPhoto> createCalendarPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId,
            @RequestParam("babyId") Integer babyId,
            @RequestParam("date") LocalDateTime date) throws IOException {
        return ResponseEntity.ok(calendarPhotoService.createCalendarPhoto(file, userId, babyId, date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarPhoto(@PathVariable Integer id) {
        calendarPhotoService.deleteCalendarPhoto(id);
        return ResponseEntity.ok().build();
    }
}