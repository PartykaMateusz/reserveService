package com.reserve.reserveService.event;

import com.reserve.reserveService.event.internal.EventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable final String id) {
        logger.trace("getting {}", id);
        return eventService.getEvent(id);
    }

    @PostMapping
    public String createEvent(@RequestBody final EventDto eventDto) {
        logger.trace("creating new event {}", eventDto.getName());
        return eventService.createEvent(eventDto);
    }
}
