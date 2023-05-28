package com.reserve.arenamanagement.arena.internal;

import com.reserve.arenamanagement.arena.ArenaService;
import com.reserve.arenamanagement.arena.internal.dto.ArenaDto;
import com.reserve.arenamanagement.arena.internal.dto.CreateArenaRequest;
import com.reserve.arenamanagement.arena.internal.dto.UpdateArenaRequest;
import com.reserve.arenamanagement.arena.internal.entity.Arena;
import com.reserve.arenamanagement.arena.internal.exception.ArenaNotFoundException;
import com.reserve.arenamanagement.arena.internal.exception.SectorAlreadyExist;
import com.reserve.arenamanagement.arena.internal.exception.SectorNotFoundException;
import com.reserve.arenamanagement.arena.internal.repository.ArenaRepository;
import com.reserve.arenamanagement.event.internal.entity.Event;
import com.reserve.arenamanagement.sector.internal.dto.SectorDto;
import com.reserve.arenamanagement.sector.internal.entity.Seat;
import com.reserve.arenamanagement.sector.internal.entity.Sector;
import com.reserve.arenamanagement.sector.internal.entity.Status;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        logger.info("Arena with id {}, successfully updated", id);

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

        logger.info("Arena with id {}, successfully updated", id);

        return arenaMapper.map(updatedArena);
    }

    @Override
    public List<ArenaDto> getAllArenas() {
        List<Arena> arenas = arenaRepository.findAll();
        return arenas.stream()
                .map(arenaMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteArena(@NonNull final String id) {
        boolean exists = arenaRepository.existsById(id);
        if (exists) {
            arenaRepository.deleteById(id);
            logger.info("Arena with id {}, successfully deleted", id);
        } else {
            throw new ArenaNotFoundException("Arena not found");
        }
    }

    @Override
    public Event attachEventToArena(@NonNull final Event event, @NonNull  final String arenaId) {
        final Arena arena = arenaRepository.findById(arenaId)
                .orElseThrow(() -> new ArenaNotFoundException("Arena not found with ID: " + arenaId));
        logger.info("Attaching arena with id: {}, name: {} attached to event ID: {}, name: {}",
                arenaId, arena.getName(), event.getId(), event.getId());
        event.setArena(arena);
        return event;
    }

    @Override
    public SectorDto addSector(@NonNull final String arenaId,
                               @NonNull final Sector sector) {
        final Arena arena = arenaRepository.findById(arenaId)
                .orElseThrow(() -> new ArenaNotFoundException("Arena not found with ID: " + arenaId));
        logger.info("Attaching sector name: {} attached to arena ID: {}, name: {}",
                sector.getName(), arenaId, arena.getName());
        checkSectorAlreadyExist(arena, sector);
        arena.addSector(sector);
        arenaRepository.save(arena);
        return arenaMapper.map(sector);
    }

    @Override
    public List<SectorDto> getArenaSectors(@NonNull final String arenaId) {
        final Arena arena = arenaRepository.findById(arenaId)
                .orElseThrow(() -> new ArenaNotFoundException("Arena not found with ID: " + arenaId));
        return arena.getSectors().stream().map(arenaMapper::map).toList();
    }

    @Override
    public SectorDto updateSector(@NonNull final String arenaId,
                                  @NonNull final String sectorId,
                                  @NonNull final Sector sector) {
        final Arena arena = arenaRepository.findById(arenaId)
                .orElseThrow(() -> new ArenaNotFoundException("Arena not found with ID: " + arenaId));
        List<Sector> actualSectors = arena.getSectors();
        Sector sectorToUpdate = findSectorById(sectorId, actualSectors)
                .orElseThrow(() -> new SectorNotFoundException("Sector " + sector.getId() + "not found in arena " +arenaId));
        sectorToUpdate.setName(sector.getName());
        sectorToUpdate.setRows(sector.getRows());
        arenaRepository.save(arena);
        return arenaMapper.map(sector);
    }

    @Override
    public SectorDto getSector(@NonNull final String arenaId,
                               @NonNull final String sectorId) {
        final Arena arena = arenaRepository.findById(arenaId)
                .orElseThrow(() -> new ArenaNotFoundException("Arena not found with ID: " + arenaId));
        List<Sector> actualSectors = arena.getSectors();
        Sector sector = findSectorById(sectorId, actualSectors)
                .orElseThrow(() -> new SectorNotFoundException("Sector " + sectorId + " not found in arena " +arenaId));
        return arenaMapper.map(sector);
    }

    @Override
    public SectorDto reserveSeat(@NonNull final String arenaId,
                                 @NonNull final String sectorId,
                                 @NonNull final Long rowNumber,
                                 @NonNull final Long seatNumber) {
        final Arena arena = arenaRepository.findById(arenaId)
                .orElseThrow(() -> new ArenaNotFoundException("Arena not found with ID: " + arenaId));
        List<Sector> actualSectors = arena.getSectors();
        Sector sector = findSectorById(sectorId, actualSectors)
                .orElseThrow(() -> new SectorNotFoundException("Sector " + sectorId + " not found in arena " +arenaId));

        final Seat seat = sector.getSeat(rowNumber, seatNumber);
        seat.setStatus(Status.RESERVED);
       // kafkaTemplate.publish(seat);
        return arenaMapper.map(sector);
    }

    private Optional<Sector> findSectorById(@NonNull final String sectorId,
                                            @NonNull final List<Sector> actualSectors) {
        return actualSectors.stream().filter(sector -> sector.getId().equals(sectorId)).findFirst();
    }

    private void checkSectorAlreadyExist(@NonNull final Arena arena,
                                         @NonNull final Sector sector) {
        boolean sectorExists = arena.getSectors().stream()
                .anyMatch(sec -> sec.getName().equals(sector.getName()));
        if (sectorExists) {
            throw new SectorAlreadyExist("Sector " + sector.getName()+" already exist in arena " + arena.getName());
        }
    }
}
