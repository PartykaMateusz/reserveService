package com.reserve.reserveService.arena;

import com.reserve.reserveService.arena.internal.dto.arena.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.arena.CreateArenaRequest;
import com.reserve.reserveService.arena.internal.dto.arena.UpdateArenaRequest;
import com.reserve.reserveService.event.internal.entity.Event;

import java.util.List;

public interface ArenaService {
    String createArena(CreateArenaRequest createArenaRequest);

    ArenaDto getArena(String id);

    ArenaDto updateArena(String id, UpdateArenaRequest updateArenaRequest);

    ArenaDto partialUpdateArena(String id, UpdateArenaRequest partialUpdateArenaRequest);

    List<ArenaDto> getAllArenas();

    void deleteArena(String id);

    Event attachEventToArena(Event event, String arenaId);
}
