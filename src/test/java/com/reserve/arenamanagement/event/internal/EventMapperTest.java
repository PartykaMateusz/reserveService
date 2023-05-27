package com.reserve.arenamanagement.event.internal;

import com.reserve.arenamanagement.arena.internal.entity.Arena;
import com.reserve.arenamanagement.event.internal.dto.CreateEventRequest;
import com.reserve.arenamanagement.event.internal.dto.EventDto;
import com.reserve.arenamanagement.event.internal.entity.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventMapperTest {

    private EventMapper eventMapper;

    @BeforeEach
    void setUp() {
        eventMapper = new EventMapper();
    }

    @Test
    void mapCreateEventRequestToEvent() {
        // Create a CreateEventRequest
        CreateEventRequest createEventRequest = new CreateEventRequest();
        createEventRequest.setName("Test Event");
        createEventRequest.setDescription("This is a test event");
        createEventRequest.setDateTime(LocalDateTime.of(2023, 5, 18, 20, 0));

        // Map CreateEventRequest to Event
        Event event = eventMapper.map(createEventRequest);

        // Verify the mapping
        assertNotNull(event);
        assertEquals("Test Event", event.getName());
        assertEquals("This is a test event", event.getDescription());
        assertEquals(LocalDateTime.of(2023, 5, 18, 20, 0), event.getDateTime());
    }

    @Test
    void mapEventToEventDto() {
        // Create an Event
        Event event = new Event();
        event.setId("testId");
        event.setName("Test Event");
        event.setDescription("This is a test event");
        event.setDateTime(LocalDateTime.of(2023, 5, 18, 20, 0));
        event.setArena(generateArena());

        // Map Event to EventDto
        EventDto eventDto = eventMapper.map(event);

        // Verify the mapping
        assertNotNull(eventDto);
        assertEquals("testId", eventDto.getId());
        assertEquals("Test Event", eventDto.getName());
        assertEquals("This is a test event", eventDto.getDescription());
        assertEquals(LocalDateTime.of(2023, 5, 18, 20, 0), eventDto.getDateTime());
        assertEquals("testArenaId", eventDto.getArenaId());
        assertEquals("testName", eventDto.getArenaName());
    }

    private Arena generateArena() {
        Arena arena = new Arena();
        arena.setId("testArenaId");
        arena.setName("testName");
        arena.setDescription("testDesc");
        return arena;
    }
}
