package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
class ArenaMapper {
    public Arena map(@NonNull final CreateArenaRequest createArenaRequest) {
        Arena arena = new Arena();
        arena.setName(createArenaRequest.getName());
        arena.setDescription(createArenaRequest.getDescription());
        return arena;
    }

    public ArenaDto map(@NonNull final Arena arena) {
        ArenaDto arenaDto = new ArenaDto();
        arenaDto.setId(arena.getId());
        arenaDto.setName(arena.getName());
        arenaDto.setDescription(arena.getDescription());
        return arenaDto;
    }
}
