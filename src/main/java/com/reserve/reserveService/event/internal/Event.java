package com.reserve.reserveService.event.internal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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

}
