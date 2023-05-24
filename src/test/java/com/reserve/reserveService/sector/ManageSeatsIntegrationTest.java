package com.reserve.reserveService.sector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.entity.Arena;
import com.reserve.reserveService.arena.internal.repository.ArenaRepository;
import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import com.reserve.reserveService.event.internal.dto.EventDto;
import com.reserve.reserveService.event.internal.entity.Event;
import com.reserve.reserveService.sector.internal.dto.CreateRowRequest;
import com.reserve.reserveService.sector.internal.dto.CreateSectorRequest;
import com.reserve.reserveService.sector.internal.dto.SectorDto;
import com.reserve.reserveService.sector.internal.entity.Row;
import com.reserve.reserveService.sector.internal.entity.Seat;
import com.reserve.reserveService.sector.internal.entity.Sector;
import com.reserve.reserveService.sector.internal.entity.Status;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ManageSeatsIntegrationTest {

    private static final String ARENA_ID = "arenaId";

    public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo"));

    @Autowired
    private ManageSeatsController eventController;

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
    }

    @Test
    void addSector() throws Exception {
        CreateSectorRequest createSectorRequest = new CreateSectorRequest();
        createSectorRequest.setName("testName");
        createSectorRequest.setCreateRowsRequest(generateRowRequest());

        final Arena arena = getArena();

        arenaRepository.save(arena);

        String sectorDtoJson = objectMapper.writeValueAsString(createSectorRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/arena/" + ARENA_ID + "/sector")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sectorDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Optional<Arena> optionalResult = arenaRepository.findById(ARENA_ID);

        assertTrue(optionalResult.isPresent());
        Arena result = optionalResult.get();

        // check if event id db equals data from dto
        assertEquals(1, result.getSectors().size());
        assertEquals(createSectorRequest.getName(), result.getSectors().get(0).getName());
        assertEquals(createSectorRequest.getCreateRowsRequest().size(), result.getSectors().get(0).getRows().size());
    }

    @Test
    void addSector_2SameNamesInSameArena_ShouldReturnBadRequest() throws Exception {
        CreateSectorRequest createSectorRequest = new CreateSectorRequest();
        createSectorRequest.setName("testName");
        createSectorRequest.setCreateRowsRequest(generateRowRequest());

        final Arena arena = getArena();

        arenaRepository.save(arena);

        String sectorDtoJson = objectMapper.writeValueAsString(createSectorRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/arena/" + ARENA_ID + "/sector")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sectorDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/arena/" + ARENA_ID + "/sector")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sectorDtoJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    void getArenaSectors() throws Exception {
        Arena arena = getArena();
        arena.addSector(getSector("A"));
        arena.addSector(getSector("B"));
        arenaRepository.save(arena);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/arena/"+ARENA_ID+"/sector")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<SectorDto> sectors = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<List<SectorDto>>() {});

        assertEquals(2, sectors.size());

        assertEquals("A", sectors.get(0).getName());
        assertEquals(5, sectors.get(0).getRows().size());
        assertEquals(50, sectors.get(0).getCapacity());

        assertEquals("B", sectors.get(1).getName());
        assertEquals(5, sectors.get(1).getRows().size());
        assertEquals(50, sectors.get(1).getCapacity());
    }

    private Sector getSector(String name) {
        Sector sector = new Sector();

        sector.setName(name);
        sector.setRows(getRows(5,10));

        return sector;
    }

    private List<Row> getRows(int rowQuantity, int seatInRowQuantity) {
        List<Row> rows = new ArrayList<>();

        for (int i = 1; i <= rowQuantity ; i++) {
            rows.add(new Row((long) i, getSeats(seatInRowQuantity)));
        }

        return rows;
    }

    private List<Seat> getSeats(int seats) {
        List<Seat> seatList = new ArrayList<>();

        for (int i = 1 ; i <= seats; i++) {
            seatList.add(new Seat((long) i, Status.AVAILABLE));
        }

        return seatList;
    }

    private Arena getArena() {
        Arena arena = new Arena();
        arena.setId(ARENA_ID);
        return arena;
    }

    private List<CreateRowRequest> generateRowRequest() {
        CreateRowRequest row1 = new CreateRowRequest();
        row1.setNumber(1L);
        row1.setSeats(10L);

        CreateRowRequest row2 = new CreateRowRequest();
        row2.setNumber(1L);
        row2.setSeats(10L);

        CreateRowRequest row3 = new CreateRowRequest();
        row3.setNumber(1L);
        row3.setSeats(10L);

        return Arrays.asList(row1, row2, row3);
    }
}
