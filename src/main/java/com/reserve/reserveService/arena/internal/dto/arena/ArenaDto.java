package com.reserve.reserveService.arena.internal.dto.arena;

import com.reserve.reserveService.arena.internal.dto.seats.SectorDto;
import lombok.Data;

import java.util.List;

@Data
public class ArenaDto {

    private String id;

    private String name;

    private String description;

    private List<SectorDto> sectors;

    private int capacity;

}
