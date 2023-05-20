package com.reserve.reserveService.event;

import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;

public interface EventService {
    String createEvent(CreateEventRequest eventDto);

    EventDto getEvent(String id);
}
