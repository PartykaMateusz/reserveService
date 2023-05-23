package com.reserve.reserveService.arena.internal.dto;

import lombok.Data;

@Data
public class CreateArenaRequest {

    @Unique
    private String name;

    private String description;
}
