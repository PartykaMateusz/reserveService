package com.reserve.arenamanagement.arena.internal;

import com.reserve.arenamanagement.arena.ArenaService;
import com.reserve.arenamanagement.arena.internal.dto.ArenaDto;
import com.reserve.arenamanagement.arena.internal.dto.CreateArenaRequest;
import com.reserve.arenamanagement.arena.internal.dto.UpdateArenaRequest;
import com.reserve.arenamanagement.arena.internal.entity.Arena;
import com.reserve.arenamanagement.arena.internal.exception.ArenaNotFoundException;
import com.reserve.arenamanagement.arena.internal.exception.SectorAlreadyExist;
import com.reserve.arenamanagement.arena.internal.repository.ArenaRepository;
import com.reserve.arenamanagement.event.internal.entity.Event;
import com.reserve.arenamanagement.sector.internal.dto.SectorDto;
import com.reserve.arenamanagement.sector.internal.entity.Row;
import com.reserve.arenamanagement.sector.internal.entity.Seat;
import com.reserve.arenamanagement.sector.internal.entity.Sector;
import com.reserve.arenamanagement.sector.internal.entity.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ArenaServiceTest {

    private static final String TEST_ID = "testId";

    private static final String TEST_NAME = "testName";

    private static final String TEST_DESCRIPTION = "testDesc";

    ArenaService arenaService;

    @Mock
    private ArenaRepository arenaRepository;

    @Mock
    private ArenaMapper arenaMapper;

    public ArenaServiceTest() {
        MockitoAnnotations.openMocks(this);
        arenaService = new ArenaServiceImpl(arenaMapper, arenaRepository);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void createArena_requestIsNull_ShouldThrowNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            arenaService.createArena(null);
        });
    }

    @Test
    void createArena_ShouldReturnEventId() {
        // Mock dependencies
        CreateArenaRequest createArenaRequest = new CreateArenaRequest();
        Arena arena = generateArena();

        when(arenaRepository.save(any(Arena.class))).thenReturn(arena);
        when(arenaMapper.map(createArenaRequest)).thenReturn(arena);

        // Call the method under test
        String arenaId = arenaService.createArena(createArenaRequest);

        // Verify the result
        assertEquals(TEST_ID, arenaId);
    }

    @Test
    void getArenaById_WhenNotExist_ShouldReturnNotFound() {
        when(arenaRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(ArenaNotFoundException.class, () -> {
            arenaService.getArena(TEST_ID);
        });
    }

    @Test
    void updateArena() {
        // Create a sample update request
        UpdateArenaRequest updateRequest = new UpdateArenaRequest();
        updateRequest.setName("New Arena Name");
        updateRequest.setDescription("New Arena Description");

        // Create a sample arena entity
        Arena arena = new Arena();
        arena.setId("sampleId");
        arena.setName("Old Arena Name");
        arena.setDescription("Old Arena Description");

        // Create a sample updated arena DTO
        ArenaDto updatedArenaDto = new ArenaDto();
        updatedArenaDto.setId("sampleId");
        updatedArenaDto.setName("New Arena Name");
        updatedArenaDto.setDescription("New Arena Description");

        // Stub the mapper behavior
        when(arenaMapper.map(any(Arena.class))).thenReturn(updatedArenaDto);

        // Mock the repository method call
        when(arenaRepository.findById(anyString())).thenReturn(java.util.Optional.of(arena));
        when(arenaRepository.save(any(Arena.class))).thenReturn(arena);

        // Invoke the service method
        ArenaDto actualArenaDto = arenaService.updateArena("sampleId", updateRequest);

        // Verify the repository interactions
        verify(arenaRepository).findById("sampleId");
        verify(arenaRepository).save(arena);

        // Verify the mapper interaction
        verify(arenaMapper).map(arena);

        // Assert the updated arena DTO
        Assertions.assertEquals(updatedArenaDto, actualArenaDto);
    }

    @Test
    void partialUpdateArena() {
        // Create a sample partial update request
        UpdateArenaRequest partialUpdateRequest = new UpdateArenaRequest();
        partialUpdateRequest.setName("Updated Arena Name");

        // Create a sample arena entity
        Arena arena = new Arena();
        arena.setId("sampleId");
        arena.setName("Old Arena Name");
        arena.setDescription("Old Arena Description");

        // Create a sample partially updated arena DTO
        ArenaDto updatedArenaDto = new ArenaDto();
        updatedArenaDto.setId("sampleId");
        updatedArenaDto.setName("Updated Arena Name");
        updatedArenaDto.setDescription("Old Arena Description");

        // Stub the mapper behavior
        when(arenaMapper.map(any(Arena.class))).thenReturn(updatedArenaDto);

        // Mock the repository method call
        when(arenaRepository.findById(anyString())).thenReturn(java.util.Optional.of(arena));
        when(arenaRepository.save(any(Arena.class))).thenReturn(arena);

        // Invoke the service method
        ArenaDto actualArenaDto = arenaService.partialUpdateArena("sampleId", partialUpdateRequest);

        // Verify the repository interactions
        verify(arenaRepository).findById("sampleId");
        verify(arenaRepository).save(arena);

        // Verify the mapper interaction
        verify(arenaMapper).map(arena);

        // Assert the updated arena DTO
        Assertions.assertEquals(updatedArenaDto, actualArenaDto);
    }

    @Test
    void getAllArenas() {
        // Create sample arenas
        Arena arena1 = new Arena();
        arena1.setId("1");
        arena1.setName("Arena 1");

        Arena arena2 = new Arena();
        arena2.setId("2");
        arena2.setName("Arena 2");

        // Create sample arena DTOs
        ArenaDto arenaDto1 = new ArenaDto();
        arenaDto1.setId("1");
        arenaDto1.setName("Arena 1 DTO");

        ArenaDto arenaDto2 = new ArenaDto();
        arenaDto2.setId("2");
        arenaDto2.setName("Arena 2 DTO");

        // Stub the repository behavior
        when(arenaRepository.findAll()).thenReturn(Arrays.asList(arena1, arena2));

        // Stub the mapper behavior
        when(arenaMapper.map(arena1)).thenReturn(arenaDto1);
        when(arenaMapper.map(arena2)).thenReturn(arenaDto2);

        // Invoke the service method
        List<ArenaDto> actualArenaDtos = arenaService.getAllArenas();

        // Verify the repository interactions
        verify(arenaRepository).findAll();

        // Verify the mapper interactions
        verify(arenaMapper).map(arena1);
        verify(arenaMapper).map(arena2);

        // Assert the result
        Assertions.assertEquals(Arrays.asList(arenaDto1, arenaDto2), actualArenaDtos);
    }

    @Test
    void deleteArena_Exists() {
        // Mock repository behavior
        when(arenaRepository.existsById("sampleId")).thenReturn(true);

        // Invoke the service method
        arenaService.deleteArena("sampleId");

        // Verify the repository interactions
        verify(arenaRepository).existsById("sampleId");
        verify(arenaRepository).deleteById("sampleId");
    }

    @Test
    void deleteArena_NotExists() {
        // Mock repository behavior
        when(arenaRepository.existsById("sampleId")).thenReturn(false);

        // Invoke the service method and assert the exception
        Assertions.assertThrows(ArenaNotFoundException.class, () -> {
            arenaService.deleteArena("sampleId");
        });

        // Verify the repository interaction
        verify(arenaRepository).existsById("sampleId");
        verify(arenaRepository, never()).deleteById(anyString());
    }

    @Test
    void attachEventToArena_NotExists() {
        when(arenaRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ArenaNotFoundException.class, () -> {
            arenaService.attachEventToArena(generateEvent(), "sampleId");
        });
    }

    @Test
    void attachEventToArena_ArenaExist() {
        Arena arena = generateArena();
        when(arenaRepository.findById(any())).thenReturn(Optional.of(arena));
        Event event = generateEvent();

        assertNull(event.getArena());

        Event result = arenaService.attachEventToArena(generateEvent(), "sampleId");

        assertEquals(arena, result.getArena());
    }

    @Test
    void addSector() {
        String arenaId = "exampleArenaId";
        Sector sector = new Sector();

        Arena arena = new Arena();
        arena.setId(arenaId);
        arena.setName("Example Arena");

        when(arenaRepository.findById(arenaId)).thenReturn(Optional.of(arena));
        when(arenaRepository.save(any())).thenReturn(arena);
        when(arenaMapper.map(any(Sector.class))).thenReturn(new SectorDto());

        SectorDto sectorDto = arenaService.addSector(arenaId, sector);

        // Assert
        verify(arenaRepository, times(1)).findById(arenaId);
        verify(arenaRepository, times(1)).save(arena);
        verify(arenaMapper, times(1)).map(sector);
    }

    @Test
    void addSector_AlreadyExist_ShouldThrowException() {
        String arenaId = "exampleArenaId";
        Sector sector = new Sector();
        sector.setName("testId");

        Sector sector2 = new Sector();
        sector2.setName("testId");

        Arena arena = new Arena();
        arena.setId(arenaId);
        arena.setName("Example Arena");
        arena.addSector(sector);

        when(arenaRepository.findById(arenaId)).thenReturn(Optional.of(arena));
        when(arenaRepository.save(any())).thenReturn(arena);
        when(arenaMapper.map(any(Sector.class))).thenReturn(new SectorDto());

        Assertions.assertThrows(SectorAlreadyExist.class, () -> {
            arenaService.addSector(arenaId, sector);;
        });

        // Assert
        verify(arenaRepository, times(1)).findById(arenaId);
        verify(arenaRepository, never()).save(arena);
        verify(arenaMapper, never()).map(sector);
    }

    @Test
    void getArenaSectors() {
        Arena arena = generateArena();
        arena.setSectors(generateSectors());

        when(arenaRepository.findById("arenaId")).thenReturn(Optional.of(arena));

        List<SectorDto> sectorDtos = arenaService.getArenaSectors("arenaId");

        assertEquals(2, sectorDtos.size());
    }

    @Test
    void updateSector() {
        //given
        Arena existingArena;
        List<Sector> existingSectors = new ArrayList<>();
        existingSectors.add(new Sector("1", "Sector 1", new ArrayList<>()));
        existingSectors.add(new Sector("2", "Sector 2", new ArrayList<>()));
        existingArena = new Arena();
        existingArena.setId("1");
        existingArena.setName("Arena 1");
        existingArena.setSectors(existingSectors);
        Sector updatedSector = new Sector("2", "Updated Sector", new ArrayList<>());

        //when
        when(arenaRepository.findById("1")).thenReturn(Optional.of(existingArena));

        // Call the updateSector method
        SectorDto updatedSectorDto = arenaService.updateSector("1", "2", updatedSector);

        // Verify that the arenaRepository.save() method was called once
        verify(arenaRepository, times(1)).save(existingArena);
        // Verify that the arenaMapper.map() method was called once
        verify(arenaMapper, times(1)).map(updatedSector);
        // Capture the argument passed to arenaRepository.save()
        ArgumentCaptor<Arena> arenaCaptor = ArgumentCaptor.forClass(Arena.class);
        verify(arenaRepository).save(arenaCaptor.capture());

        // Assert the captured argument
        Arena capturedArena = arenaCaptor.getValue();
        assertNotNull(capturedArena);

        // Assert that the updatedSector was applied to the capturedArena
        Optional<Sector> capturedSector = findSectorById("2", capturedArena.getSectors());
        assertTrue(capturedSector.isPresent());
        assertEquals("Updated Sector", capturedSector.get().getName());
    }

    @Test
    void getSector() {
        // Create a Arena object
        Arena arena = new Arena();
        arena.setId("1");
        arena.setName("Arena 1");
        List<Sector> sectors = generateSectors();
        arena.setSectors(sectors);

        // Create a mock Sector object
        //Sector sector = new Sector("1", "Sector 1", new ArrayList<>());

        // Mock the behavior of arenaRepository.findById()
        when(arenaRepository.findById("1")).thenReturn(Optional.of(arena));

        // Create a SectorDto object
        SectorDto sectorDto = new SectorDto();
        sectorDto.setId("1");
        sectorDto.setName("sector 1");
        sectorDto.setRows(new ArrayList<>());

        // Mock the behavior of arenaMapper.map()
        when(arenaMapper.map(sectors.get(0))).thenReturn(sectorDto);

        // Call the getSector method
        SectorDto result = arenaService.getSector("1", "sectorAId");

        // Verify that arenaRepository.findById() is called once with the correct argument
        verify(arenaRepository, times(1)).findById("1");

        // Verify that arenaMapper.map() is called once with the correct argument
        verify(arenaMapper, times(1)).map(sectors.get(0));

        // Assert the result
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("sector 1", result.getName());
    }

    private Optional<Sector> findSectorById(String sectorId, List<Sector> sectors) {
        return sectors.stream().filter(sector -> sector.getId().equals(sectorId)).findFirst();
    }
    private List<Sector> generateSectors() {
        Sector sector1 = new Sector();
        sector1.setName("A");
        sector1.setRows(generateRows());
        sector1.setId("sectorAId");

        Sector sector2 = new Sector();
        sector2.setName("B");
        sector2.setRows(generateRows());
        sector2.setId("sectorBId");

        return List.of(sector1, sector2);
    }

    private List<Row> generateRows() {
        Row row1 = new Row(1L, generateSeats(10));
        Row row2 = new Row(2L, generateSeats(10));
        Row row3 = new Row(3L, generateSeats(10));

        return Arrays.asList(row1, row2, row3);
    }

    private List<Seat> generateSeats(int number) {
        List<Seat> seats = new ArrayList<>();
        for(int i=1 ; i<number ; i++) {
            seats.add(new Seat((long) i, Status.AVAILABLE));
        }
        return seats;
    }

    private Event generateEvent() {
        Event event = new Event();
        event.setId("testEventId");
        event.setName("name");
        event.setDescription("desc");
        event.setDateTime(LocalDateTime.of(2023,5,23,20,0));
        return event;
    }

    private Arena generateArena() {
        Arena arena = new Arena();
        arena.setId(TEST_ID);
        arena.setName(TEST_NAME);
        arena.setDescription(TEST_DESCRIPTION);
        return arena;
    }

}