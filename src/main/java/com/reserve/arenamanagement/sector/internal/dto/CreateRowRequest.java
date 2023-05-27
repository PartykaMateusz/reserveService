package com.reserve.arenamanagement.sector.internal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRowRequest {

    @NotBlank
    private Long number;

    @NotBlank
    private Long seats;
}
