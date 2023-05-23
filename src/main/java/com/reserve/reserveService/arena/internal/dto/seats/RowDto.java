package com.reserve.reserveService.arena.internal.dto.seats;

import lombok.Data;

import java.util.List;

@Data
public class RowDto {

    private Long number;

    private List<SeatDto> seats;

    public int getCapacity() {
        return seats.size();
    }
}

