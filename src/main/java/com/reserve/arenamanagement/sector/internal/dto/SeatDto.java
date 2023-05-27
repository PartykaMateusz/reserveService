package com.reserve.arenamanagement.sector.internal.dto;

import com.reserve.arenamanagement.sector.internal.entity.Status;
import lombok.Data;

@Data
public class SeatDto {

    private Long number;

    private Status status;
}