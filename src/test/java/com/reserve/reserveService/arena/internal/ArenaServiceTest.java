package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.ArenaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class ArenaServiceTest {

    private static final String TEST_ID = "testId";
    private static final String TEST_NAME = "testName";

    private static final String TEST_DESCRIPTION = "testDesc";

    ArenaService arenaService;
    private ArenaDto arenaDto;

    public ArenaServiceTest() {
        MockitoAnnotations.openMocks(this);
        arenaService = new ArenaServiceImpl();
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void createArena_ShouldReturnArenaId() {
        String arenaId = arenaService.createArena(arenaDto);

        assertEquals(TEST_ID, arenaId);
    }
}