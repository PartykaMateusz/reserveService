package com.reserve.reserveService.arena.internal.dto.seats;

import lombok.Data;

import java.util.List;

@Data
public class SectorDto {

    private String name;

    private List<RowDto> rows;

    public int getCapacity() {
        return rows.stream()
                .mapToInt(RowDto::getCapacity)
                .sum();
    }
}
