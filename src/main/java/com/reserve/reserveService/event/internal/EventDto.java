package com.reserve.reserveService.event.internal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public final class EventDto {

    private String id;
    private String name;
    private String description;
    private LocalDateTime dateTime;

}
