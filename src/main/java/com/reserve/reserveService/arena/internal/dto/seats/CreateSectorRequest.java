package com.reserve.reserveService.arena.internal.dto.seats;

import lombok.Data;

import java.util.List;

@Data
public class CreateSectorRequest {

    private String arenaId;

    private String name;

    private List<CreateRowRequest> createRowsRequest;
}
