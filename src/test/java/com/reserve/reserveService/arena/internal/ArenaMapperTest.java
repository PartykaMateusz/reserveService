package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ArenaMapperTest {

    private ArenaMapper arenaMapper;

    @BeforeEach
    void setUp() {
        arenaMapper = new ArenaMapper();
    }

    @Test
    void mapCreateArenaRequestToArena() {
        CreateArenaRequest createArenaRequest = new CreateArenaRequest();
        createArenaRequest.setName("Test Arena");
        createArenaRequest.setDescription("This is a test arena");

        Arena arena = arenaMapper.map(createArenaRequest);

        assertNotNull(arena);
        assertEquals("Test Arena", arena.getName());
        assertEquals("This is a test arena", arena.getDescription());
    }

    @Test
    void mapArenaToArenaDto() {
        Arena arena = new Arena();
        arena.setId("testId");
        arena.setName("Test Arena");
        arena.setDescription("This is a test arena");

        ArenaDto arenaDto = arenaMapper.map(arena);

        assertNotNull(arenaDto);
        assertEquals("testId", arenaDto.getId());
        assertEquals("Test Arena", arenaDto.getName());
        assertEquals("This is a test arena", arenaDto.getDescription());
    }
}
