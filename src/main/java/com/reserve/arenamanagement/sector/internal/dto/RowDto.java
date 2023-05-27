package com.reserve.arenamanagement.sector.internal.dto;

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

