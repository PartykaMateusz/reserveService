package com.reserve.arenamanagement.sector.internal;

import com.reserve.arenamanagement.arena.ArenaService;
import com.reserve.arenamanagement.sector.SectorService;
import com.reserve.arenamanagement.sector.internal.dto.CreateSectorRequest;
import com.reserve.arenamanagement.sector.internal.dto.ReserveSeatRequest;
import com.reserve.arenamanagement.sector.internal.dto.SectorDto;
import com.reserve.arenamanagement.sector.internal.entity.Sector;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SectorServiceImpl implements SectorService {

    static final Logger logger = LoggerFactory.getLogger(SectorServiceImpl.class);
    private final ArenaService arenaService;

    public SectorServiceImpl(final ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @Override
    public SectorDto addSector(@NonNull final String arenaId,
                               @NonNull final CreateSectorRequest createSectorRequest) {
        logger.trace("adding new sector to arena with ID: {}", arenaId);
        Sector sector = SectorFactory.createSector(createSectorRequest);
        SectorDto sectorDto = arenaService.addSector(arenaId, sector);
        logger.info("Sector successfully added, name: {}", sectorDto.getName());
        return sectorDto;
    }

    @Override
    public SectorDto getSector(@NonNull final String arenaId,
                               @NonNull final String sectorId) {
        return arenaService.getSector(arenaId, sectorId);
    }

    @Override
    public List<SectorDto> getSectors(@NonNull final String arenaId) {
        return arenaService.getArenaSectors(arenaId);
    }

    @Override
    public SectorDto updateSector(@NonNull final String arenaId,
                                  @NonNull final String sectorId,
                                  @NonNull final CreateSectorRequest createSectorRequest) {
        logger.trace("updating sector in arena with ID: {}, sector: {}", arenaId, createSectorRequest.getName());
        Sector sector = SectorFactory.createSector(createSectorRequest);
        SectorDto sectorDto = arenaService.updateSector(arenaId, sectorId, sector);
        logger.info("Sector updated, arenaId: {}, sector name: {}", arenaId, sectorDto.getName());
        return sectorDto;
    }

    @Override
    public SectorDto reserveSeat(@NonNull final String arenaId,
                                 @NonNull final String sectorId,
                                 @NonNull final ReserveSeatRequest reserveSeatRequest) {
        logger.trace("reserving seats in arena ID: {}, sector: {}, row: {}, seat: {}",
                arenaId,
                sectorId,
                reserveSeatRequest.getRow(),
                reserveSeatRequest.getSeat());
        SectorDto sectorDto = arenaService.reserveSeat(arenaId, sectorId, reserveSeatRequest.getRow(), reserveSeatRequest.getSeat());
        logger.trace("successfully reserved seats in arena ID: {}, sector: {}, row: {}, seat: {}",
                arenaId,
                sectorId,
                reserveSeatRequest.getRow(),
                reserveSeatRequest.getSeat());
        return sectorDto;
    }


}
