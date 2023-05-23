package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.internal.dto.arena.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.arena.CreateArenaRequest;
import com.reserve.reserveService.arena.internal.dto.seats.RowDto;
import com.reserve.reserveService.arena.internal.dto.seats.SeatDto;
import com.reserve.reserveService.arena.internal.dto.seats.SectorDto;
import com.reserve.reserveService.arena.internal.entity.Arena;
import com.reserve.reserveService.arena.internal.entity.Row;
import com.reserve.reserveService.arena.internal.entity.Seat;
import com.reserve.reserveService.arena.internal.entity.Sector;
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
        arenaDto.setSectors(map(arena.getSectors()));
        arenaDto.setCapacity(arena.getCapacity());
        return arenaDto;
    }

    private List<SectorDto> map(@NonNull final  List<Sector> sectors) {
        return sectors.stream().map(this::map).toList();
    }

    private SectorDto map(@NonNull final Sector sector) {
        SectorDto sectorDto = new SectorDto();
        sectorDto.setName(sector.getName());
        sectorDto.setRows(sector.getRows().stream().map(this::map).toList());
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
