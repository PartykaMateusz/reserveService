package com.reserve.reserveService.event.internal.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public final class EventDto {

    private String id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String arenaId;
    private String arenaName;
}
