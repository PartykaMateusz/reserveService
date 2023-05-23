package com.reserve.reserveService.arena.internal.dto.seats;

import lombok.Data;

@Data
public class CreateRowRequest {

    private Long number;
    private Long seats;
}
