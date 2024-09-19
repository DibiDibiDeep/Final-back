package com.example.finalproj.CalendarPhoto.controller;

import com.example.finalproj.CalendarPhoto.entity.CalendarPhoto;
import com.example.finalproj.CalendarPhoto.service.CalendarPhotoService;
import com.example.finalproj.ml.service.MLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/calendar-photos")
public class CalendarPhotoController {

    private final CalendarPhotoService calendarPhotoService;

    public CalendarPhotoController(CalendarPhotoService calendarPhotoService) {
        this.calendarPhotoService = calendarPhotoService;
    }

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
    public ResponseEntity<List<CalendarPhoto>> getCalendarPhotosByTakenAt(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(calendarPhotoService.getCalendarPhotosByDate(date.atStartOfDay()));
    }

    @GetMapping("/year/{year}/month/{month}")
    public ResponseEntity<List<CalendarPhoto>> getCalendarPhotosByYearAndMonth(
            @PathVariable int year,
            @PathVariable int month) {
        List<CalendarPhoto> photos = calendarPhotoService.getCalendarPhotosByYearAndMonth(year, month);
        return ResponseEntity.ok(photos);
    }


    @PostMapping
    public ResponseEntity<?> createCalendarPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId,
            @RequestParam("babyId") Integer babyId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        try {
            CalendarPhoto photo = calendarPhotoService.createCalendarPhoto(file, userId, babyId, date);
            return ResponseEntity.ok(photo);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarPhoto(@PathVariable Integer id) {
        calendarPhotoService.deleteCalendarPhoto(id);
        return ResponseEntity.ok().build();
    }
}
