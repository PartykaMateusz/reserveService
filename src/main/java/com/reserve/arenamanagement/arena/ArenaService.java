package com.reserve.arenamanagement.arena;

import com.reserve.arenamanagement.arena.internal.dto.ArenaDto;
import com.reserve.arenamanagement.arena.internal.dto.CreateArenaRequest;
import com.reserve.arenamanagement.arena.internal.dto.UpdateArenaRequest;
import com.reserve.arenamanagement.event.internal.entity.Event;
import com.reserve.arenamanagement.sector.internal.dto.SectorDto;
import com.reserve.arenamanagement.sector.internal.entity.Sector;

import java.util.List;

public interface ArenaService {
    String createArena(CreateArenaRequest createArenaRequest);

    ArenaDto getArena(String id);

    ArenaDto updateArena(String id, UpdateArenaRequest updateArenaRequest);

    ArenaDto partialUpdateArena(String id, UpdateArenaRequest partialUpdateArenaRequest);

    List<ArenaDto> getAllArenas();

    void deleteArena(String id);

    Event attachEventToArena(Event event, String arenaId);

    SectorDto addSector(String arenaId, Sector sector);

    List<SectorDto> getArenaSectors(String arenaId);

    SectorDto updateSector(String arenaId, String sectorId, Sector sector);

    SectorDto getSector(String arenaId, String sectorId);

    SectorDto reserveSeat(String arenaId, String sectorId, Long row, Long seat);
}
