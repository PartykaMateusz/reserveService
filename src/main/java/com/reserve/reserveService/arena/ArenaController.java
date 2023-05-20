package com.reserve.reserveService.arena;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/arena")
public class ArenaController {

    private final ArenaService arenaService;

    public ArenaController(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @PostMapping
    public ResponseEntity<String> createArena() {
        return new ResponseEntity<>(arenaService.createArena(null), HttpStatus.CREATED);
    }
}
