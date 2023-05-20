package com.reserve.reserveService.arena.internal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "events")
@Data
public class Arena {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;
}
