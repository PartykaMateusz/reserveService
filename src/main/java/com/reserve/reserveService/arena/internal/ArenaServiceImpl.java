package com.reserve.reserveService.arena.internal;

import com.reserve.reserveService.arena.ArenaService;
import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import com.reserve.reserveService.arena.internal.dto.UpdateArenaRequest;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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
        final Arena arena = arenaRepository.findById(id)
                .orElseThrow(() -> new ArenaNotFoundException("Arena not found with ID: " + id));
        return arenaMapper.map(arena);
    }

    @Override
    public ArenaDto updateArena(@NonNull final String id,
                                @NonNull final UpdateArenaRequest updateArenaRequest) {
        final Arena arena = arenaRepository.findById(id)
                .orElseThrow(() -> new ArenaNotFoundException("Arena not found with ID: " + id));

        arena.setName(updateArenaRequest.getName());
        arena.setDescription(updateArenaRequest.getDescription());

        final Arena updatedArena = arenaRepository.save(arena);

        return arenaMapper.map(updatedArena);
    }

    @Override
    public ArenaDto partialUpdateArena(@NonNull final String id,
                                       @NonNull final UpdateArenaRequest partialUpdateArenaRequest) {
        final Arena arena = arenaRepository.findById(id)
                .orElseThrow(() -> new ArenaNotFoundException("Arena not found with ID: " + id));

        if (partialUpdateArenaRequest.getName() != null) {
            arena.setName(partialUpdateArenaRequest.getName());
        }
        if (partialUpdateArenaRequest.getDescription() != null) {
            arena.setDescription(partialUpdateArenaRequest.getDescription());
        }

        final Arena updatedArena = arenaRepository.save(arena);

        return arenaMapper.map(updatedArena);
    }

    @Override
    public List<ArenaDto> getAllArenas() {
        return null;
    }

    @Override
    public String deleteArena(@NonNull final String id) {
        return null;
    }
}
