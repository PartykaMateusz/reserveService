package com.reserve.reserveService.sector.internal;

import com.reserve.reserveService.arena.ArenaService;
import com.reserve.reserveService.sector.SectorService;
import com.reserve.reserveService.sector.internal.dto.CreateSectorRequest;
import com.reserve.reserveService.sector.internal.dto.SectorDto;
import com.reserve.reserveService.sector.internal.entity.Sector;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}
