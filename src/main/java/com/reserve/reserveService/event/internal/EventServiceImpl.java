package com.reserve.reserveService.event.internal;

import com.reserve.reserveService.event.EventService;
import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import com.reserve.reserveService.event.internal.dto.UpdateEventRequest;
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

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public String createEvent(@NonNull final CreateEventRequest createEventRequest) {
        logger.info("Creating new event {}, date: {}", createEventRequest.getName(), createEventRequest.getDateTime());
        final Event result = eventRepository.save(eventMapper.map(createEventRequest));
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
