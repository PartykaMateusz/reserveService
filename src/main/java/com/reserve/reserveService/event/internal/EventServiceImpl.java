package com.reserve.reserveService.event.internal;

import com.reserve.reserveService.arena.ArenaService;
import com.reserve.reserveService.arena.internal.entity.Arena;
import com.reserve.reserveService.event.EventService;
import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import com.reserve.reserveService.event.internal.dto.UpdateEventRequest;
import com.reserve.reserveService.event.internal.entity.Event;
import com.reserve.reserveService.event.internal.exception.EventNotFoundException;
import com.reserve.reserveService.event.internal.repository.EventRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class EventServiceImpl implements EventService {

    static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ArenaService arenaService;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, ArenaService arenaService) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.arenaService = arenaService;
    }

    @Override
    public String createEvent(@NonNull final CreateEventRequest createEventRequest) {
        logger.info("Creating new event {}, date: {}", createEventRequest.getName(), createEventRequest.getDateTime());
        final Event event = eventMapper.map(createEventRequest);
        arenaService.attachEventToArena(event, createEventRequest.getArenaId());
        final Event result = eventRepository.save(event);
        logger.info("Created event: {}", result.getId());
        return result.getId();
    }

    @Override
    public EventDto getEvent(@NonNull final String id) {
        final Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));
        return eventMapper.map(event);
    }

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto updateEvent(String id, UpdateEventRequest updateEventRequest) {
        final Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));

        event.setName(updateEventRequest.getName());
        event.setDescription(updateEventRequest.getDescription());
        event.setDateTime(updateEventRequest.getDateTime());

        final Event updatedEvent = eventRepository.save(event);

        logger.info("Event with id {}, successfully updated", id);

        return eventMapper.map(updatedEvent);
    }

    @Override
    public EventDto partialUpdateEvent(String id, UpdateEventRequest updateEventRequest) {
        final Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));

        if (updateEventRequest.getName() != null) {
            event.setName(updateEventRequest.getName());
        }
        if (updateEventRequest.getDescription() != null) {
            event.setDescription(updateEventRequest.getDescription());
        }
        if (updateEventRequest.getDateTime() != null) {
            event.setDateTime(updateEventRequest.getDateTime());
        }

        final Event updatedEvent = eventRepository.save(event);

        logger.info("Event with id {}, successfully updated", id);

        return eventMapper.map(updatedEvent);
    }

    @Override
    public void deleteEvent(String id) {
        boolean exists = eventRepository.existsById(id);
        if (exists) {
            eventRepository.deleteById(id);
            logger.info("Event with id {}, successfully deleted", id);
        } else {
            throw new EventNotFoundException("Event not found");
        }
    }
}
