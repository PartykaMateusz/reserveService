package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.ArenaService;
import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import com.reserve.reserveService.event.internal.Event;
import com.reserve.reserveService.event.internal.dto.CreateEventRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

    private Arena generateArena() {
        Arena arena = new Arena();
        arena.setId(TEST_ID);
        arena.setName(TEST_NAME);
        arena.setDescription(TEST_DESCRIPTION);
        return arena;
    }


}