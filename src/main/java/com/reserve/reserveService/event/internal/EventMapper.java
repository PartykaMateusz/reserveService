package com.reserve.reserveService.event.internal;

import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import com.reserve.reserveService.event.internal.entity.Event;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
class EventMapper {

    static final Logger logger = LoggerFactory.getLogger(EventMapper.class);

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

        if (Objects.isNull(event.getArena())) {
            return eventDto;
        }

        logger.error("Event with ID: {} doesn't have arena", event.getId());
        eventDto.setArenaId(event.getArena().getId());
        eventDto.setArenaName(event.getArena().getName());
        return eventDto;
    }
}
