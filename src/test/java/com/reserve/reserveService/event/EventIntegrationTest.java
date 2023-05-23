package com.reserve.reserveService.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reserve.reserveService.event.internal.Event;
import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import com.reserve.reserveService.event.internal.EventRepository;
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
        eventRepository.deleteAll();
    }


    @Test
    void createEvent() throws Exception {
        CreateEventRequest createEventRequest = new CreateEventRequest();
        createEventRequest.setName("testName");
        createEventRequest.setDescription("testDescription");
        createEventRequest.setDateTime(LocalDateTime.of(2023,5,18,20,0));

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

    }

    @Test
    void create2EventWithSameName_ShouldReturnBadRequest() throws Exception {
        CreateEventRequest createEventRequest = new CreateEventRequest();
        createEventRequest.setName("testName");
        createEventRequest.setDescription("testDescription");
        createEventRequest.setDateTime(LocalDateTime.of(2023,5,18,20,0));

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
}
