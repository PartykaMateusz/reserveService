package com.reserve.arenamanagement.sector.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReserveSeatRequest {

    private Long row;
    private Long seat;
}
