package com.reserve.reserveService.event;

import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event map(@NonNull final EventDto eventDto) {
        final Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setDateTime(eventDto.getDateTime());
        return event;
    }

    public EventDto map(@NonNull final Event event) {
        final EventDto eventDto = new EventDto();
        eventDto.setName(event.getName());
        eventDto.setDescription(event.getDescription());
        eventDto.setDateTime(event.getDateTime());
        return eventDto;
    }
}
