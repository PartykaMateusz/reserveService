package com.reserve.reserveService.sector;

import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.sector.internal.dto.CreateSectorRequest;
import com.reserve.reserveService.sector.internal.dto.SectorDto;

public interface SectorService {
    SectorDto addSector(String arenaId, CreateSectorRequest createSectorRequest);
}
