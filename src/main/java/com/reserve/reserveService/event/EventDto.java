package com.reserve.reserveService.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EventDto {
    private String name;
    private String description;
    private LocalDateTime dateTime;
}
