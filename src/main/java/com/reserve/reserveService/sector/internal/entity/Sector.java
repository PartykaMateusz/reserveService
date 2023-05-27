package com.reserve.reserveService.sector.internal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

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
