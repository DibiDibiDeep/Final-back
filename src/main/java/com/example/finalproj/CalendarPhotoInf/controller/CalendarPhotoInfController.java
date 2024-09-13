package com.example.finalproj.CalendarPhotoInf.controller;

import com.example.finalproj.CalendarPhotoInf.entity.CalendarPhotoInf;
import com.example.finalproj.CalendarPhotoInf.service.CalendarPhotoInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar-photo-inf")
public class CalendarPhotoInfController {

    @Autowired
    private CalendarPhotoInfService calendarPhotoInfService;

    @PostMapping("/{calendarPhotoId}")
    public ResponseEntity<CalendarPhotoInf> saveAndProcessInferenceResult(
            @PathVariable Integer calendarPhotoId,
            @RequestBody String inferenceResult) {
        CalendarPhotoInf processedResult = calendarPhotoInfService.saveAndProcessInferenceResult(calendarPhotoId, inferenceResult);
        return ResponseEntity.ok(processedResult);
    }

    @GetMapping
    public ResponseEntity<List<CalendarPhotoInf>> getAllCalendarPhotoInfs() {
        List<CalendarPhotoInf> calendarPhotoInfs = calendarPhotoInfService.getAllCalendarPhotoInfs();
        return ResponseEntity.ok(calendarPhotoInfs);
    }
}