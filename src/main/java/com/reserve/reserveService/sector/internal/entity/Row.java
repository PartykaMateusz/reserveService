package com.reserve.reserveService.sector.internal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Row {

    private Long number;

    private List<Seat> seats;

    public int getCapacity() {
        return seats.size();
    }


}
