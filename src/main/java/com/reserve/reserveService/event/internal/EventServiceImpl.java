package com.reserve.reserveService.event.internal;

import com.reserve.reserveService.event.EventController;
import com.reserve.reserveService.event.EventService;
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
    public String createEvent(@NonNull final EventDto eventDto) {
        logger.info("Creating new event {}, date: {}", eventDto.getName(), eventDto.getDateTime());
        final Event result = eventRepository.save(eventMapper.map(eventDto));
        logger.info("Created event: {}", result.getId());
        return result.getId();
    }

    @Override
    public EventDto getEvent(@NonNull final String id) {
        final Event event = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
        return eventMapper.map(event);
    }
}
