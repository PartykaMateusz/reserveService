package com.reserve.reserveService.arena;

import com.reserve.reserveService.arena.internal.dto.ArenaDto;
import com.reserve.reserveService.arena.internal.dto.CreateArenaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        logger.trace("getting {}", id);
        return ResponseEntity.ok(arenaService.getArena(id));
    }

    @PostMapping
    public ResponseEntity<String> createArena(@RequestBody @Validated CreateArenaRequest createArenaRequest) {
        return new ResponseEntity<>(arenaService.createArena(createArenaRequest), HttpStatus.CREATED);
    }
}
