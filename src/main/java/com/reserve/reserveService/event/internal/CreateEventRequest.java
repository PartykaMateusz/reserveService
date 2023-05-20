package com.reserve.reserveService.event.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public final class CreateEventRequest {

    @NotBlank
    @Size(min = 3, max = 25)
    private String name;

    private String description;

    @NotNull
    private LocalDateTime dateTime;

}
