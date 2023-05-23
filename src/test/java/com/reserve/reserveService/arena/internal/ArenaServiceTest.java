package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.ArenaService;
import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import com.reserve.reserveService.arena.internal.dto.UpdateArenaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void testUpdateArena() {
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
    void testPartialUpdateArena() {
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

    private Arena generateArena() {
        Arena arena = new Arena();
        arena.setId(TEST_ID);
        arena.setName(TEST_NAME);
        arena.setDescription(TEST_DESCRIPTION);
        return arena;
    }


}