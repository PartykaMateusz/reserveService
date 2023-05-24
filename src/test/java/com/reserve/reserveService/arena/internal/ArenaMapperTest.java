package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import com.reserve.reserveService.sector.internal.dto.RowDto;
import com.reserve.reserveService.sector.internal.dto.SectorDto;
import com.reserve.reserveService.arena.internal.entity.*;
import com.reserve.reserveService.sector.internal.entity.Row;
import com.reserve.reserveService.sector.internal.entity.Seat;
import com.reserve.reserveService.sector.internal.entity.Sector;
import com.reserve.reserveService.sector.internal.entity.Status;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArenaMapperTest {
    private ArenaMapper arenaMapper;

    @BeforeEach
    void setUp() {
        arenaMapper = new ArenaMapper();
    }

    @Test
    void testMap_CreateArenaRequestToArena() {
        // Create sample CreateArenaRequest
        CreateArenaRequest createArenaRequest = new CreateArenaRequest();
        createArenaRequest.setName("Arena 1");
        createArenaRequest.setDescription("Sample arena");

        // Map CreateArenaRequest to Arena
        Arena arena = arenaMapper.map(createArenaRequest);

        // Assert mapping result
        assertEquals(createArenaRequest.getName(), arena.getName());
        assertEquals(createArenaRequest.getDescription(), arena.getDescription());
    }

    @Test
    void testMap_ArenaToArenaDto() {
        Arena arena = new Arena();
        arena.setId("testId");
        arena.setName("Arena 1");
        arena.setDescription("Sample arena");

        Sector sector1 = new Sector();
        sector1.setName("Sector 1");

        Sector sector2 = new Sector();
        sector2.setName("Sector 2");

        Seat seat1 = new Seat(1L, Status.OCCUPIED );
        Seat seat2 = new Seat(2L, Status.AVAILABLE);

        Row row1 = new Row(1L, List.of(seat1));
        Row row2 = new Row(2L, List.of(seat2));

        sector1.setRows(List.of(row1));
        sector2.setRows(List.of(row2));

        arena.setSectors(Arrays.asList(sector1, sector2));

        ArenaDto arenaDto = arenaMapper.map(arena);

        assertEquals(arena.getId(), arenaDto.getId());
        assertEquals(arena.getName(), arenaDto.getName());
        assertEquals(arena.getDescription(), arenaDto.getDescription());

        List<SectorDto> sectorDtos = arenaDto.getSectors();
        assertEquals(2, sectorDtos.size());

        SectorDto sectorDto1 = sectorDtos.get(0);
        List<RowDto> rowDtos1 = sectorDto1.getRows();
        assertEquals(1, rowDtos1.size());
        assertEquals(row1.getNumber(), rowDtos1.get(0).getNumber());

        SectorDto sectorDto2 = sectorDtos.get(1);
        List<RowDto> rowDtos2 = sectorDto2.getRows();
        assertEquals(1, rowDtos2.size());
        assertEquals(row2.getNumber(), rowDtos2.get(0).getNumber());
    }
}
