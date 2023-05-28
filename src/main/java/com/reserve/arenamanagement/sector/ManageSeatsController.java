package com.reserve.arenamanagement.sector;

import com.reserve.arenamanagement.sector.internal.dto.CreateSectorRequest;
import com.reserve.arenamanagement.sector.internal.dto.ReserveSeatRequest;
import com.reserve.arenamanagement.sector.internal.dto.SectorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/arena") public class ManageSeatsController {

    private final SectorService sectorService;

    public ManageSeatsController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @PostMapping("/{arenaId}/sector")
    public ResponseEntity<SectorDto> addSector(@PathVariable final String arenaId,
                                               @RequestBody @Validated final CreateSectorRequest createSectorRequest) {
        SectorDto sectorDto = sectorService.addSector(arenaId, createSectorRequest);
        return ResponseEntity.ok(sectorDto);
    }

    @GetMapping("/{arenaId}/sector/{sectorId}")
    public ResponseEntity<SectorDto> getSector(@PathVariable final String arenaId,
                                               @PathVariable final String sectorId) {
        SectorDto sectorDto = sectorService.getSector(arenaId, sectorId);
        return ResponseEntity.ok(sectorDto);
    }

    @GetMapping("/{arenaId}/sector")
    public ResponseEntity<List<SectorDto>> getArenaSectors(@PathVariable final String arenaId) {
        List<SectorDto> sectorDto = sectorService.getSectors(arenaId);
        return ResponseEntity.ok(sectorDto);
    }

    @PutMapping("/{arenaId}/sector/{sectorId}")
    public ResponseEntity<SectorDto> updateSector(@PathVariable final String arenaId,
                                                  @PathVariable final String sectorId,
                                                  @RequestBody @Validated final CreateSectorRequest createSectorRequest) {
        SectorDto sectorDto = sectorService.updateSector(arenaId, sectorId, createSectorRequest);
        return ResponseEntity.ok(sectorDto);
    }

    @PostMapping("/{arenaId}/sector/{sectorId}/reserve")
    public ResponseEntity<SectorDto> reserveSeat(@PathVariable final String arenaId,
                                                 @PathVariable final String sectorId,
                                                 @RequestBody @Validated final ReserveSeatRequest reserveSeatRequest) {
        SectorDto sectorDto = sectorService.reserveSeat(arenaId, sectorId, reserveSeatRequest);
        return ResponseEntity.ok(null);
    }
}
