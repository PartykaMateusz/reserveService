package com.reserve.reserveService.event;

public interface EventService {
    String createEvent(EventDto eventDto);

    EventDto getEvent(String id);
}
