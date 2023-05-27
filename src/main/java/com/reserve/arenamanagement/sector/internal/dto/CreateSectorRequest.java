package com.reserve.arenamanagement.sector.internal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateSectorRequest {

    @NotBlank
    private String name;

    @NotEmpty
    private List<CreateRowRequest> createRowsRequest;
}
