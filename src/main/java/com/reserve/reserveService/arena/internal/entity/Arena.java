package com.reserve.reserveService.arena.internal.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "events")
@Data
public class Arena {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;

    private List<Sector> sectors = new ArrayList<>();

    public int getCapacity() {
        return sectors.stream()
                .mapToInt(Sector::getCapacity)
                .sum();
    }
}
