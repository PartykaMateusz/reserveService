package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import com.reserve.reserveService.arena.internal.dto.SectorInArenaDto;
import com.reserve.reserveService.sector.internal.dto.RowDto;
import com.reserve.reserveService.sector.internal.dto.SeatDto;
import com.reserve.reserveService.sector.internal.dto.SectorDto;
import com.reserve.reserveService.arena.internal.entity.Arena;
import com.reserve.reserveService.sector.internal.entity.Row;
import com.reserve.reserveService.sector.internal.entity.Seat;
import com.reserve.reserveService.sector.internal.entity.Sector;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ArenaMapper {
    public Arena map(@NonNull final CreateArenaRequest createArenaRequest) {
        Arena arena = new Arena();
        arena.setName(createArenaRequest.getName());
        arena.setDescription(createArenaRequest.getDescription());
        return arena;
    }

    public ArenaDto map(@NonNull final Arena arena) {
        ArenaDto arenaDto = new ArenaDto();
        arenaDto.setId(arena.getId());
        arenaDto.setName(arena.getName());
        arenaDto.setDescription(arena.getDescription());
        arenaDto.setSectors(mapToSectorInArena(arena.getSectors()));
        arenaDto.setCapacity(arena.getCapacity());
        return arenaDto;
    }

    private List<SectorInArenaDto> mapToSectorInArena(@NonNull final List<Sector> sectors) {
        return sectors.stream().map(this::mapToSectorInArena).toList();
    }

    private SectorInArenaDto mapToSectorInArena(@NonNull final Sector sector) {
        SectorInArenaDto sectorDto = new SectorInArenaDto();
        sectorDto.setName(sector.getName());
        sectorDto.setCapacity(sector.getCapacity());
        sectorDto.setId(sector.getId());
        return sectorDto;
    }

    private List<SectorDto> map(@NonNull final List<Sector> sectors) {
        return sectors.stream().map(this::map).toList();
    }

    public SectorDto map(@NonNull final Sector sector) {
        SectorDto sectorDto = new SectorDto();
        sectorDto.setName(sector.getName());
        sectorDto.setRows(sector.getRows().stream().map(this::map).toList());
        sectorDto.setId(sector.getId());
        return sectorDto;
    }

    private RowDto map(@NonNull final Row row) {
        RowDto rowDto = new RowDto();
        rowDto.setNumber(row.getNumber());
        rowDto.setSeats(row.getSeats().stream().map(this::map).toList());
        return rowDto;
    }

    private SeatDto map(@NonNull final Seat seat) {
        SeatDto seatDto = new SeatDto();
        seatDto.setNumber(seat.getNumber());
        seatDto.setStatus(seat.getStatus());
        return seatDto;
    }
}
