package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.internal.dto.seats.CreateRowRequest;
import com.reserve.reserveService.arena.internal.dto.seats.CreateSectorRequest;
import com.reserve.reserveService.arena.internal.entity.Row;
import com.reserve.reserveService.arena.internal.entity.Seat;
import com.reserve.reserveService.arena.internal.entity.Sector;
import com.reserve.reserveService.arena.internal.entity.Status;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

class SectorFactory {

    public static Sector createSector(@NonNull final CreateSectorRequest request) {
        final List<CreateRowRequest> rowsRequest = request.getCreateRowsRequest();
        final List<Row> rows = rowsRequest.stream().map(SectorFactory::createRow).toList();

        final Sector sector = new Sector();
        sector.setName(request.getName());
        sector.setRows(rows);

        return sector;
    }

    protected static Row createRow(@NonNull final CreateRowRequest rowRequest) {
        return new Row(rowRequest.getNumber(), getSeats(rowRequest.getSeats()));
    }

    protected static List<Seat> getSeats(@NonNull final Long seatQuantity) {
        final List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= seatQuantity; ++i) {
            seats.add(new Seat((long) i, Status.AVAILABLE));
        }
        return seats;
    }
}