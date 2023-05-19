package com.reserve.reserveService.event;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public String createEvent(@NonNull final EventDto eventDto) {
        Event event = eventMapper.map(eventDto);
        final Event result = eventRepository.save(event);
        return result.getId();
    }

    @Override
    public EventDto getEvent(@NonNull final String id) {
        final Event event = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
        return eventMapper.map(event);
    }
}
