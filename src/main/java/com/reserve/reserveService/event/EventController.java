package com.reserve.reserveService.event;

import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import com.reserve.reserveService.event.internal.dto.UpdateEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable final String id) {
        logger.trace("getting {}", id);
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody @Validated final CreateEventRequest createEventRequest) {
        logger.trace("creating new event {}", createEventRequest.getName());
        return new ResponseEntity<>(eventService.createEvent(createEventRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        logger.trace("Getting all events");
        List<EventDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable String id, @RequestBody @Validated UpdateEventRequest updateEventRequest) {
        logger.trace("Updating event with ID: {}", id);
        EventDto updateEvent = eventService.updateEvent(id, updateEventRequest);
        return ResponseEntity.ok(updateEvent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventDto> partialUpdateEvent(@PathVariable String id, @RequestBody @Validated UpdateEventRequest updateEventRequest) {
        logger.trace("Partially updating event with ID: {}", id);
        EventDto updateEvent = eventService.partialUpdateEvent(id, updateEventRequest);
        return ResponseEntity.ok(updateEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        logger.trace("Deleting event with ID: {}", id);
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
