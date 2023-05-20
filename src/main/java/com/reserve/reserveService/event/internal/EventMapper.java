package com.reserve.reserveService.event.internal;

import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
 class EventMapper {

    public Event map(@NonNull final CreateEventRequest createEventRequest) {
        final Event event = new Event();
        event.setName(createEventRequest.getName());
        event.setDescription(createEventRequest.getDescription());
        event.setDateTime(createEventRequest.getDateTime());
        return event;
    }

    public EventDto map(@NonNull final Event event) {
        final EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setDescription(event.getDescription());
        eventDto.setDateTime(event.getDateTime());
        return eventDto;
    }
}
