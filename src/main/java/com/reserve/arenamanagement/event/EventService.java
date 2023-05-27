package com.reserve.arenamanagement.event;

import com.reserve.arenamanagement.event.internal.dto.CreateEventRequest;
import com.reserve.arenamanagement.event.internal.dto.EventDto;
import com.reserve.arenamanagement.event.internal.dto.UpdateEventRequest;

import java.util.List;

public interface EventService {
    String createEvent(CreateEventRequest eventDto);

    EventDto getEvent(String id);

    List<EventDto> getAllEvents();

    EventDto updateEvent(String id, UpdateEventRequest updateEventRequest);

    EventDto partialUpdateEvent(String id, UpdateEventRequest updateEventRequest);

    void deleteEvent(String id);
}
