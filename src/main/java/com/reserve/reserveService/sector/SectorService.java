package com.reserve.reserveService.sector;

import com.reserve.reserveService.sector.internal.dto.CreateSectorRequest;
import com.reserve.reserveService.sector.internal.dto.SectorDto;

import java.util.List;

public interface SectorService {
    SectorDto addSector(String arenaId, CreateSectorRequest createSectorRequest);

    List<SectorDto> getSector(String arenaId);
}
