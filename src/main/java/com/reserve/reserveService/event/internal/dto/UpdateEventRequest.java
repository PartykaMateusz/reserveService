package com.reserve.reserveService.event.internal.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateEventRequest {

    private String name;
    private String description;
    private LocalDateTime dateTime;
}
