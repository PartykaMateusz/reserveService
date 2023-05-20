package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.ArenaService;
import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

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


}