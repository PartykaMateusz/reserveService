package com.reserve.reserveService.event.internal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "events")
@Data
public class Event {

    @Id
    private String id;

    private LocalDateTime dateTime;

    private String name;

    private String description;

}
