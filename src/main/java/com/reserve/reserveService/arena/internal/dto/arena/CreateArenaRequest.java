package com.reserve.reserveService.arena.internal.dto.arena;

import com.reserve.reserveService.arena.internal.dto.Unique;
import lombok.Data;

@Data
public class CreateArenaRequest {

    @Unique
    private String name;

    private String description;
}
