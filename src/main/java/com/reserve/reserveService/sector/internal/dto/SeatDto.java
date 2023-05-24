package com.reserve.reserveService.sector.internal.dto;

import com.reserve.reserveService.sector.internal.entity.Status;
import lombok.Data;

@Data
public class SeatDto {

    private Long number;

    private Status status;
}