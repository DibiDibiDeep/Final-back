package com.deep.backend.model.service;

import com.deep.backend.model.dao.EventMapper;
import com.deep.backend.model.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public void createEvent(EventDTO eventDTO) {
        eventMapper.createEvent(eventDTO);
    }

    public EventDTO getEvent(int eventId) {
        return eventMapper.getEvent(eventId);
    }

    public List<EventDTO> getAllEvents() {
        return eventMapper.getAllEvents();
    }

    public void updateEvent(int eventId, EventDTO eventDTO) {
        eventDTO.setEventId(eventId);
        eventMapper.updateEvent(eventDTO);
    }

    public void deleteEvent(int eventId) {
        eventMapper.deleteEvent(eventId);
    }
}