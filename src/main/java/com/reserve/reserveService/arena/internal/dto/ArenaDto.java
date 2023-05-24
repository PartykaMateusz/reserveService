package com.reserve.reserveService.arena.internal.dto;

import com.reserve.reserveService.sector.internal.dto.SectorDto;
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
