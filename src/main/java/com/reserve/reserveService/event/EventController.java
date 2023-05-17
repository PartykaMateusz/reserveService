package com.reserve.reserveService.event;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public String createEvent(@RequestBody final EventDto eventDto) {
        logger.trace("creating new event {}", eventDto.getName());
        return eventService.createEvent(eventDto);
    }
}
