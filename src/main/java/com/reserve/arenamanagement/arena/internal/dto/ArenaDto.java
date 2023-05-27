package com.reserve.arenamanagement.arena.internal.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArenaDto {

    private String id;

    private String name;

    private String description;

    private List<SectorInArenaDto> sectors;

    private int capacity;

}
