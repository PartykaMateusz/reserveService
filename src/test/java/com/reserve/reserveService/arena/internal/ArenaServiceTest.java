package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.ArenaService;
import com.reserve.reserveService.arena.internal.dto.arena.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.arena.CreateArenaRequest;
import com.reserve.reserveService.arena.internal.dto.arena.UpdateArenaRequest;
import com.reserve.reserveService.arena.internal.entity.Arena;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private Arena generateArena() {
        Arena arena = new Arena();
        arena.setId(TEST_ID);
        arena.setName(TEST_NAME);
        arena.setDescription(TEST_DESCRIPTION);
        return arena;
    }


}