package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.ArenaService;

class ArenaServiceImpl implements ArenaService {

    @Override
    public String createArena(ArenaDto arenaDto) {
        return this.arenaRepository.create(this.arenaMapper.map(arenaDto));
    }
}
