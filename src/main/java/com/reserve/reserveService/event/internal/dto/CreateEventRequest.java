package com.reserve.reserveService.event.internal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public final class CreateEventRequest {

    @NotBlank(message = "name is required")
    @Size(min = 3, max = 25)
    @Unique(message = "name must be unique")
    private String name;

    private String description;

    @NotNull
    private LocalDateTime dateTime;

    @NotBlank(message = "arenaId is required")
    private String arenaId;

}
