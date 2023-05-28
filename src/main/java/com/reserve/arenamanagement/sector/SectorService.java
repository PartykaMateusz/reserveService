package com.reserve.arenamanagement.sector;

import com.reserve.arenamanagement.sector.internal.dto.CreateSectorRequest;
import com.reserve.arenamanagement.sector.internal.dto.ReserveSeatRequest;
import com.reserve.arenamanagement.sector.internal.dto.SectorDto;

import java.util.List;

public interface SectorService {
    SectorDto addSector(String arenaId, CreateSectorRequest createSectorRequest);

    List<SectorDto> getSectors(String arenaId);

    SectorDto updateSector(String arenaId, String sectorId, CreateSectorRequest createSectorRequest);

    SectorDto getSector(String arenaId, String sectorId);

    SectorDto reserveSeat(String arenaId, String sectorId, ReserveSeatRequest reserveSeatRequest);
}
