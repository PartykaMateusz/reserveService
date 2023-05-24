package com.reserve.reserveService.sector.internal;

import com.reserve.reserveService.sector.internal.dto.CreateRowRequest;
import com.reserve.reserveService.sector.internal.dto.CreateSectorRequest;
import com.reserve.reserveService.sector.internal.entity.Row;
import com.reserve.reserveService.sector.internal.entity.Sector;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class SectorMapper {
//    public Sector map(@NonNull final CreateSectorRequest createSectorRequest) {
//        Sector sector = new Sector();
//        sector.setName(createSectorRequest.getName());
//        sector.setRows(map(createSectorRequest.getCreateRowsRequest()));
//        return sector;
//    }
//
//    private List<Row> map(@NonNull final List<CreateRowRequest> createRowsRequest) {
//        return createRowsRequest.stream().map(this::map).toList();
//    }
//
//    private Row map(CreateRowRequest createRowRequest) {
//        return new Row(createRowRequest.getNumber(), this.map(c))
//    }
}
