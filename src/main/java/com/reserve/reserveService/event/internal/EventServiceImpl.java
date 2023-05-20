package com.reserve.reserveService.event.internal;

import com.reserve.reserveService.event.EventService;
import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        final Event event = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
        return eventMapper.map(event);
    }
}
