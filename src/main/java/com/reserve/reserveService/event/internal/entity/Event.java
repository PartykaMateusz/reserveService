package com.reserve.reserveService.event.internal.entity;

import com.reserve.reserveService.arena.internal.entity.Arena;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "events")
@Data
public class Event {

    @Id
    private String id;

    private LocalDateTime dateTime;

    @Indexed(unique = true)
    private String name;

    private String description;

    @DBRef
    private Arena arena;

}
