package com.reserve.reserveService.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reserve.reserveService.arena.internal.entity.Arena;
import com.reserve.reserveService.arena.internal.repository.ArenaRepository;
import com.reserve.reserveService.event.internal.entity.Event;
import com.reserve.reserveService.event.internal.repository.EventRepository;
import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventIntegrationTest {

    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo"));

    @Autowired
    private EventController eventController;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArenaRepository arenaRepository;

    @BeforeAll
    void setUp() {
        container.start();
    }

    @AfterAll
    void tearDown() {
        container.stop();
    }

    @AfterEach
    void clearDatabase() {
        arenaRepository.deleteAll();
        eventRepository.deleteAll();
    }


    @Test
    void createEvent() throws Exception {
        CreateEventRequest createEventRequest = new CreateEventRequest();
        createEventRequest.setName("testName");
        createEventRequest.setDescription("testDescription");
        createEventRequest.setDateTime(LocalDateTime.of(2023,5,18,20,0));
        createEventRequest.setArenaId("arenaId");

        final Arena arena = getArena();

        arenaRepository.save(arena);

        String eventDtoJson = objectMapper.writeValueAsString(createEventRequest);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // Retrieve the response content
        String createdEventId = response.getResponse().getContentAsString();

        Optional<Event> optionalResult = eventRepository.findById(createdEventId);

        assertTrue(optionalResult.isPresent());
        Event result = optionalResult.get();

        // check if event id db equals data from dto
        assertEquals(createEventRequest.getName(), result.getName());
        assertEquals(createEventRequest.getDescription(), result.getDescription());
        assertEquals(createdEventId, result.getId());
        assertEquals(arena, result.getArena());
    }

    private Arena getArena() {
        Arena arena = new Arena();
        arena.setId("arenaId");
        return arena;
    }

    @Test
    void create2EventWithSameName_ShouldReturnBadRequest() throws Exception {
        CreateEventRequest createEventRequest = new CreateEventRequest();
        createEventRequest.setName("testName");
        createEventRequest.setDescription("testDescription");
        createEventRequest.setDateTime(LocalDateTime.of(2023,5,18,20,0));
        createEventRequest.setArenaId("arenaId");

        Arena arena = getArena();
        arenaRepository.save(arena);

        String eventDtoJson = objectMapper.writeValueAsString(createEventRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    void getEvent() throws Exception {
        Event event = new Event();
        event.setName("testName");
        event.setDescription("testDescription");
        event.setDateTime(LocalDateTime.of(2023,5,18,20,0));

        String id = eventRepository.save(event).getId();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/event/"+id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        EventDto eventDto = objectMapper.readValue(response.getResponse().getContentAsString(), EventDto.class);

        assertEquals("testName", eventDto.getName());
        assertEquals("testDescription", eventDto.getDescription());
        assertEquals(LocalDateTime.of(2023,5,18,20,0), eventDto.getDateTime());
        assertEquals(id, eventDto.getId());
    }
    @Test
    void getEvent_WhenNotFound_ShouldReturnNotFound() throws Exception {
        Event event = new Event();
        event.setName("testName");
        event.setDescription("testDescription");
        event.setDateTime(LocalDateTime.of(2023,5,18,20,0));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/event/testId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    void getAllEvents() throws Exception {
        Event event = new Event();
        event.setName("testName");
        event.setDescription("testDescription");
        Event event1 = new Event();
        event1.setName("testName2");
        event1.setDescription("testDescription2");

        eventRepository.save(event);
        eventRepository.save(event1);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/event")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<EventDto> events = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<List<EventDto>>() {});

        assertEquals(2, events.size());
        assertEquals("testName", events.get(0).getName());
        assertEquals("testDescription", events.get(0).getDescription());
        assertEquals("testName2", events.get(1).getName());
        assertEquals("testDescription2", events.get(1).getDescription());
    }
}
