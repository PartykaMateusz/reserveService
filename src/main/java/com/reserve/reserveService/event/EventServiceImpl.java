package com.reserve.reserveService.event;

import org.springframework.stereotype.Service;

@Service
class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public String createEvent(final EventDto eventDto) {
        Event result = eventRepository.save(eventMapper.map(eventDto));
        return result.getId();
    }
}
