package com.reserve.reserveService.event;

import org.springframework.stereotype.Component;

@Component
public class EventMapper {


    public Event map(EventDto eventDto) {
        final Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setDateTime(eventDto.getDateTime());
        return event;
    }
}
