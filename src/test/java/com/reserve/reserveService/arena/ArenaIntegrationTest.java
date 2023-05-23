package com.reserve.reserveService.arena;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reserve.reserveService.arena.ArenaController;
import com.reserve.reserveService.arena.internal.Arena;
import com.reserve.reserveService.arena.internal.ArenaRepository;
import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
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

import java.util.Optional;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArenaIntegrationTest {

    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo"));

    @Autowired
    private ArenaController arenaController;

    @Autowired
    private ArenaRepository arenaRepository;

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
        arenaRepository.deleteAll();
    }


    @Test
    void createArena() throws Exception {
        CreateArenaRequest createArenaRequest = new CreateArenaRequest();
        createArenaRequest.setName("testName");
        createArenaRequest.setDescription("testDescription");

        String eventDtoJson = objectMapper.writeValueAsString(createArenaRequest);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/arena")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // Retrieve the response content
        String createdArenaId = response.getResponse().getContentAsString();

        Optional<Arena> optionalResult = arenaRepository.findById(createdArenaId);

        assertTrue(optionalResult.isPresent());
        Arena result = optionalResult.get();

        // check if event id db equals data from dto
        assertEquals(createArenaRequest.getName(), result.getName());
        assertEquals(createArenaRequest.getDescription(), result.getDescription());
        assertEquals(createdArenaId, result.getId());

    }

    @Test
    void create2ArenasWithSameName_ShouldReturnBadRequest() throws Exception {
        CreateArenaRequest createArenaRequest = new CreateArenaRequest();
        createArenaRequest.setName("testName");
        createArenaRequest.setDescription("testDescription");

        String eventDtoJson = objectMapper.writeValueAsString(createArenaRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/arena")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/arena")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    void getArena() throws Exception {
        Arena arena = new Arena();
        arena.setName("testName");
        arena.setDescription("testDescription");

        String id = arenaRepository.save(arena).getId();

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/arena/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ArenaDto arenaDto = objectMapper.readValue(response.getResponse().getContentAsString(), ArenaDto.class);

        assertEquals("testName", arenaDto.getName());
        assertEquals("testDescription", arenaDto.getDescription());
        assertEquals(id, arenaDto.getId());
    }

    @Test
    void getArena_WhenNotFound_ShouldReturnNotFound() throws Exception {
        Arena arena = new Arena();
        arena.setName("testName");
        arena.setDescription("testDescription");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/arena/testId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}