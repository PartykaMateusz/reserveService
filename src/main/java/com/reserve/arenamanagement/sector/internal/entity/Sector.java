package com.reserve.arenamanagement.sector.internal.entity;

import com.reserve.arenamanagement.sector.internal.exception.RowNotExist;
import com.reserve.arenamanagement.sector.internal.exception.SeatNotAvailable;
import com.reserve.arenamanagement.sector.internal.exception.SeatNotExistInRow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sector {

    private String id;

    private String name;

    private List<Row> rows = new ArrayList<>();

    public int getCapacity() {
        return rows.stream()
                .mapToInt(Row::getCapacity)
                .sum();
    }

    public Seat getSeat(@NonNull final Long rowNumber, @NonNull final Long seatNumber) {
        Row row = getRowByNumber(rowNumber);
        if (seatNumber > row.getSeats().size()) {
            throw  new SeatNotExistInRow("Seat "+seatNumber+" not exist in row "+rowNumber);
        }
        Seat seat = row.getSeats().get(Math.toIntExact(seatNumber));
        if (seat.getStatus() != Status.AVAILABLE) {
            throw new SeatNotAvailable("Seat "+seatNumber+" in row "+rowNumber+" not available");
        }
        return seat;
    }

    private Row getRowByNumber(Long rowNumber) {
        return rows.stream()
                .filter(row -> row.getNumber().equals(rowNumber))
                .findFirst()
                .orElseThrow(() -> new RowNotExist("Row " + rowNumber + " not exist"));
    }
}
