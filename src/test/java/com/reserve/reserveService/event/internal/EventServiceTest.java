package com.reserve.reserveService.event.internal;

import com.reserve.reserveService.event.EventService;
import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EventServiceTest {

    private static final String TEST_ID = "testId";
    private static final String TEST_NAME = "testName";

    private static final String TEST_DESCRIPTION = "testDesc";

    private static final LocalDateTime TEST_DATETIME = LocalDateTime.of(2023,6,17,20,0);

    EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    public EventServiceTest() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventServiceImpl(eventRepository, eventMapper);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void createEvent_ShouldReturnEventId() {
        // Mock dependencies
        CreateEventRequest createEventRequest = new CreateEventRequest();
        Event event = generateEvent();

        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(eventMapper.map(createEventRequest)).thenReturn(event);

        // Call the method under test
        String eventId = eventService.createEvent(createEventRequest);

        // Verify the result
        assertEquals(TEST_ID, eventId);
    }
    @Test
    void getEvent_ShouldReturnEvent() {
        Event event = generateEvent();
        EventDto eventDto = generateEventDto();
        eventDto.setId(TEST_ID);

        // Mock dependencies
        when(eventRepository.findById(TEST_ID)).thenReturn(Optional.of(event));
        when(eventMapper.map(event)).thenReturn(eventDto);

        // Call the method under test
        EventDto result = eventService.getEvent(TEST_ID);

        // Verify the result
        assertEquals(TEST_NAME, result.getName());
        assertEquals(TEST_DESCRIPTION, result.getDescription());
        assertEquals(TEST_DATETIME, result.getDateTime());
        assertEquals(TEST_ID, result.getId());
    }

    @Test
    void getEvent_WhenNotExist_ShouldReturnNotFound() {
        when(eventRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(EventNotFoundException.class, () -> {
            eventService.getEvent(TEST_ID);
        });
    }

    private EventDto generateEventDto() {
        EventDto eventDto1 = new EventDto();
        eventDto1.setName(TEST_NAME);
        eventDto1.setDescription(TEST_DESCRIPTION);
        eventDto1.setDateTime(TEST_DATETIME);
        return eventDto1;
    }

    private Event generateEvent() {
        Event event = new Event();
        event.setId(TEST_ID);
        event.setName(TEST_NAME);
        event.setDescription(TEST_DESCRIPTION);
        event.setDateTime(TEST_DATETIME);
        return event;
    }
}