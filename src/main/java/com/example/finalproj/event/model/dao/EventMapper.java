package com.example.finalproj.event.model.dao;

import com.example.finalproj.event.model.dto.EventDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventMapper {
    void createEvent(EventDTO eventDTO);
    EventDTO getEvent(int eventId);
    List<EventDTO> getAllEvents();
    void updateEvent(EventDTO eventDTO);
    void deleteEvent(int eventId);
}

