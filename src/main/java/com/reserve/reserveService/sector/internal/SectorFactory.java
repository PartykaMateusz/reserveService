package com.reserve.reserveService.sector.internal;

import com.reserve.reserveService.sector.internal.dto.CreateRowRequest;
import com.reserve.reserveService.sector.internal.dto.CreateSectorRequest;
import com.reserve.reserveService.sector.internal.entity.Row;
import com.reserve.reserveService.sector.internal.entity.Seat;
import com.reserve.reserveService.sector.internal.entity.Sector;
import com.reserve.reserveService.sector.internal.entity.Status;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class SectorFactory {

    private SectorFactory() {}

    public static Sector createSector(@NonNull final CreateSectorRequest request) {
        final List<CreateRowRequest> rowsRequest = request.getCreateRowsRequest();
        final List<Row> rows = rowsRequest.stream().map(SectorFactory::createRow).toList();

        final Sector sector = new Sector();
        sector.setName(request.getName());
        sector.setRows(rows);
        sector.setId(generateId());

        return sector;
    }

    private static String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
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