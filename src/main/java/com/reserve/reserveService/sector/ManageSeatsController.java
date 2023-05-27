package com.reserve.reserveService.sector;

import com.reserve.reserveService.sector.internal.dto.CreateSectorRequest;
import com.reserve.reserveService.sector.internal.dto.SectorDto;
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

    @GetMapping("/{arenaId}/sector")
    public ResponseEntity<List<SectorDto>> addSector(@PathVariable final String arenaId) {
        List<SectorDto> sectorDto = sectorService.getSector(arenaId);
        return ResponseEntity.ok(sectorDto);
    }

    @PutMapping("/{arenaId}/sector/{sectorId}")
    public ResponseEntity<SectorDto> updateSector(@PathVariable final String arenaId,
                                                  @PathVariable final String sectorId,
                                                  @RequestBody @Validated final CreateSectorRequest createSectorRequest) {
        SectorDto sectorDto = sectorService.updateSector(arenaId, sectorId, createSectorRequest);
        return ResponseEntity.ok(sectorDto);
    }
}
