package com.reserve.arenamanagement.sector.internal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sector {

    private String id;

    private String name;

    private List<Row> rows = new ArrayList<>();

    public int getCapacity() {
        return rows.stream()
                .mapToInt(Row::getCapacity)
                .sum();
    }
}
