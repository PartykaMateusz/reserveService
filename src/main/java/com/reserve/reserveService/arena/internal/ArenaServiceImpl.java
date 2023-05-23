package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.ArenaService;
import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class ArenaServiceImpl implements ArenaService {

    static final Logger logger = LoggerFactory.getLogger(ArenaServiceImpl.class);

    private final ArenaMapper arenaMapper;
    private final ArenaRepository arenaRepository;

    public ArenaServiceImpl(final ArenaMapper arenaMapper, final ArenaRepository arenaRepository) {
        this.arenaMapper = arenaMapper;
        this.arenaRepository = arenaRepository;
    }

    @Override
    public String createArena(@NonNull final CreateArenaRequest createArenaRequest) {
        logger.info("Creating new arena {}", createArenaRequest.getName());
        final Arena result = this.arenaRepository.save(this.arenaMapper.map(createArenaRequest));
        logger.info("Created arena: {}", result.getId());
        return result.getId();
    }

    @Override
    public ArenaDto getArena(@NonNull final String id) {
        final Arena arena = arenaRepository.findById(id).orElseThrow(() -> new ArenaNotFoundException("Arena not found"));
        return arenaMapper.map(arena);
    }
}
