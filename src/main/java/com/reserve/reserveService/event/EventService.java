package com.reserve.reserveService.event;

import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import com.reserve.reserveService.event.internal.dto.UpdateEventRequest;

import java.util.List;

public interface EventService {
    String createEvent(CreateEventRequest eventDto);

    EventDto getEvent(String id);

    List<EventDto> getAllEvents();

    EventDto updateEvent(String id, UpdateEventRequest updateEventRequest);

    EventDto partialUpdateEvent(String id, UpdateEventRequest updateEventRequest);

    void deleteEvent(String id);
}
