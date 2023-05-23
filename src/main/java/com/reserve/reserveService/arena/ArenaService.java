package com.reserve.reserveService.arena;

import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import com.reserve.reserveService.arena.internal.dto.UpdateArenaRequest;

import java.util.List;

public interface ArenaService {
    String createArena(CreateArenaRequest createArenaRequest);

    ArenaDto getArena(String id);

    ArenaDto updateArena(String id, UpdateArenaRequest updateArenaRequest);

    ArenaDto partialUpdateArena(String id, UpdateArenaRequest partialUpdateArenaRequest);

    List<ArenaDto> getAllArenas();

    void deleteArena(String id);
}
