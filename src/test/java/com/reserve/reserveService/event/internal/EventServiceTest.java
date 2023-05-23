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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EventServiceTest {

    private static final String TEST_ID = "testId";
    public static final String ARENA_NAME = "testName";
    private static final String TEST_NAME = "testName";

    private static final String TEST_DESCRIPTION = "testDesc";

    private static final LocalDateTime TEST_DATETIME = LocalDateTime.of(2023,6,17,20,0);
    public static final String ARENA_ID = "testArenaId";

    public static final String ARENA_DESC = "testArenaDesc";

    EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private ArenaService arenaService;

    public EventServiceTest() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventServiceImpl(eventRepository, eventMapper, arenaService);
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
        eventDto.setArenaId(ARENA_ID);
        eventDto.setArenaName(ARENA_NAME);

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
        assertEquals(ARENA_ID, result.getArenaId());
        assertEquals(ARENA_NAME, result.getArenaName());
    }

    @Test
    void getEvent_WhenNotExist_ShouldReturnNotFound() {
        when(eventRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(EventNotFoundException.class, () -> {
            eventService.getEvent(TEST_ID);
        });
    }

    @Test
    void updateEvent() {
        // Create a sample update request
        UpdateEventRequest updateRequest = new UpdateEventRequest();
        updateRequest.setName("New Event Name");
        updateRequest.setDescription("New Event Description");
        updateRequest.setDateTime(LocalDateTime.of(2023,5,23,20,0));

        // Create a sample event entity
        Event event = new Event();
        event.setId("sampleId");
        event.setName("Old Event Name");
        event.setDescription("Old Event Description");

        // Create a sample updated event DTO
        EventDto updatedEventDto = new EventDto();
        updatedEventDto.setId("sampleId");
        updatedEventDto.setName("New Event Name");
        updatedEventDto.setDescription("New Event Description");

        // Stub the mapper behavior
        when(eventMapper.map(any(Event.class))).thenReturn(updatedEventDto);

        // Mock the repository method call
        when(eventRepository.findById(anyString())).thenReturn(java.util.Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Invoke the service method
        EventDto actualEventDto = eventService.updateEvent("sampleId", updateRequest);

        // Verify the repository interactions
        verify(eventRepository).findById("sampleId");
        verify(eventRepository).save(event);

        // Verify the mapper interaction
        verify(eventMapper).map(event);

        Assertions.assertEquals(updatedEventDto, actualEventDto);
    }

    @Test
    void partialUpdateEvent() {
        // Create a sample partial update request
        UpdateEventRequest updateRequest = new UpdateEventRequest();
        updateRequest.setName("New Event Name");

        // Create a sample event entity
        Event event = new Event();
        event.setId("sampleId");
        event.setName("Old Event Name");
        event.setDescription("Old Event Description");
        event.setDateTime(LocalDateTime.of(2023,5,23,20,0));

        // Create a sample partially updated event DTO
        EventDto updatedEventDto = new EventDto();
        updatedEventDto.setId("sampleId");
        updatedEventDto.setName("Updated Event Name");
        updatedEventDto.setDescription("Old Event Description");
        updatedEventDto.setDateTime(LocalDateTime.of(2023,5,23,20,0));

        // Stub the mapper behavior
        when(eventMapper.map(any(Event.class))).thenReturn(updatedEventDto);

        // Mock the repository method call
        when(eventRepository.findById(anyString())).thenReturn(java.util.Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Invoke the service method
        EventDto actualEventDto = eventService.partialUpdateEvent("sampleId", updateRequest);

        // Verify the repository interactions
        verify(eventRepository).findById("sampleId");
        verify(eventRepository).save(event);

        // Verify the mapper interaction
        verify(eventMapper).map(event);

        // Assert the updated event DTO
        Assertions.assertEquals(updatedEventDto, actualEventDto);
    }

    @Test
    void getAllEvents() {
        // Create sample Events
        Event event1 = new Event();
        event1.setId("1");
        event1.setName("Event 1");
        event1.setArena(generateArena());

        Event event2 = new Event();
        event2.setId("2");
        event2.setName("Event 2");
        event2.setArena(generateArena());

        // Create sample Event DTOs
        EventDto eventDto1 = new EventDto();
        eventDto1.setId("1");
        eventDto1.setName("Event 1 DTO");

        EventDto eventDto2 = new EventDto();
        eventDto2.setId("2");
        eventDto2.setName("Event 2 DTO");

        // Stub the repository behavior
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        // Stub the mapper behavior
        when(eventMapper.map(event1)).thenReturn(eventDto1);
        when(eventMapper.map(event2)).thenReturn(eventDto2);

        // Invoke the service method
        List<EventDto> actualEventDtos = eventService.getAllEvents();

        // Verify the repository interactions
        verify(eventRepository).findAll();

        // Verify the mapper interactions
        verify(eventMapper).map(event1);
        verify(eventMapper).map(event2);

        // Assert the result
        Assertions.assertEquals(Arrays.asList(eventDto1, eventDto2), actualEventDtos);
    }

    @Test
    void deleteEvent_Exists() {
        // Mock repository behavior
        when(eventRepository.existsById("sampleId")).thenReturn(true);

        // Invoke the service method
        eventService.deleteEvent("sampleId");

        // Verify the repository interactions
        verify(eventRepository).existsById("sampleId");
        verify(eventRepository).deleteById("sampleId");
    }

    @Test
    void deleteEvent_NotExists() {
        // Mock repository behavior
        when(eventRepository.existsById("sampleId")).thenReturn(false);

        // Invoke the service method and assert the exception
        Assertions.assertThrows(EventNotFoundException.class, () -> {
            eventService.deleteEvent("sampleId");
        });

        // Verify the repository interaction
        verify(eventRepository).existsById("sampleId");
        verify(eventRepository, never()).deleteById(anyString());
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

    private Arena generateArena() {
        Arena arena = new Arena();
        arena.setId(ARENA_ID);
        arena.setName(ARENA_NAME);
        arena.setDescription(ARENA_DESC);
        return arena;
    }
}