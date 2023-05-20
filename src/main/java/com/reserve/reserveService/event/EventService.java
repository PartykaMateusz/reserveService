package com.reserve.reserveService.event;

import com.reserve.reserveService.event.internal.EventDto;

public interface EventService {
    String createEvent(EventDto eventDto);

    EventDto getEvent(String id);
}
