package com.reserve.reserveService.arena.internal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Seat {

    private Long number;

    private Status status;
}
