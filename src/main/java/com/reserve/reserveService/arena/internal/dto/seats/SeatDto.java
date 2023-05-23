package com.reserve.reserveService.arena.internal.dto.seats;

import com.reserve.reserveService.arena.internal.entity.Status;
import lombok.Data;

@Data
public class SeatDto {

    private Long number;

    private Status status;
}