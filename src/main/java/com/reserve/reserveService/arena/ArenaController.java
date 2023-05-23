package com.reserve.reserveService.arena;

import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import com.reserve.reserveService.arena.internal.dto.UpdateArenaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/arena")
public class ArenaController {

    static final Logger logger = LoggerFactory.getLogger(ArenaController.class);

    private final ArenaService arenaService;

    public ArenaController(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArenaDto> getArena(@PathVariable final String id) {
        logger.trace("Getting arena with ID: {}", id);
        return ResponseEntity.ok(arenaService.getArena(id));
    }


    @GetMapping
    public ResponseEntity<List<ArenaDto>> getAllArenas() {
        logger.trace("Getting all arenas");
        List<ArenaDto> arenas = arenaService.getAllArenas();
        return ResponseEntity.ok(arenas);
    }

    @PostMapping
    public ResponseEntity<String> createArena(@RequestBody @Validated CreateArenaRequest createArenaRequest) {
        return new ResponseEntity<>(arenaService.createArena(createArenaRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArenaDto> updateArena(@PathVariable String id, @RequestBody @Validated UpdateArenaRequest updateArenaRequest) {
        logger.trace("Updating arena with ID: {}", id);
        ArenaDto updatedArenaDto = arenaService.updateArena(id, updateArenaRequest);
        return ResponseEntity.ok(updatedArenaDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArenaDto> partialUpdateArena(@PathVariable String id, @RequestBody @Validated UpdateArenaRequest partialUpdateArenaRequest) {
        logger.trace("Partially updating arena with ID: {}", id);
        ArenaDto updatedArenaDto = arenaService.partialUpdateArena(id, partialUpdateArenaRequest);
        return ResponseEntity.ok(updatedArenaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArena(@PathVariable String id) {
        logger.trace("Deleting arena with ID: {}", id);
        arenaService.deleteArena(id);
        return ResponseEntity.noContent().build();
    }
}
