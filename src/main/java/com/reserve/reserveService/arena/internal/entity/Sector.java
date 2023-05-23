package com.reserve.reserveService.arena.internal.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Sector {

    private String name;

    private List<Row> rows = new ArrayList<>();

    public int getCapacity() {
        return rows.stream()
                .mapToInt(Row::getCapacity)
                .sum();
    }
}
