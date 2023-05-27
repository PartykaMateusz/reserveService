package com.reserve.reserveService.sector.internal;

import com.reserve.reserveService.sector.internal.dto.CreateRowRequest;
import com.reserve.reserveService.sector.internal.dto.CreateSectorRequest;
import com.reserve.reserveService.sector.internal.entity.Row;
import com.reserve.reserveService.sector.internal.entity.Seat;
import com.reserve.reserveService.sector.internal.entity.Sector;
import com.reserve.reserveService.sector.internal.entity.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class SectorFactoryTest {

    @Test
    void createSector() {
        CreateRowRequest rowRequest1 = Mockito.mock(CreateRowRequest.class);
        CreateRowRequest rowRequest2 = Mockito.mock(CreateRowRequest.class);

        CreateSectorRequest request = new CreateSectorRequest();
        request.setCreateRowsRequest(List.of(rowRequest1, rowRequest2));
        request.setName("arena-1");

        Sector createdSector = SectorFactory.createSector(request);

        // Assertions
        Assertions.assertEquals("arena-1", createdSector.getName());
        Assertions.assertEquals(2, createdSector.getRows().size());
        Assertions.assertNotNull(createdSector.getId());
    }

    @Test
    void createRow() {
        CreateRowRequest rowRequest = new CreateRowRequest();
        rowRequest.setNumber(1L);
        rowRequest.setSeats(2L);

        Seat seat1 = new Seat(1L, Status.AVAILABLE);
        Seat seat2 = new Seat(1L, Status.AVAILABLE);

        Row expectedRow = new Row(1L, List.of(seat1, seat2));

        Row createdRow = SectorFactory.createRow(rowRequest);

        // Assertions
        Assertions.assertEquals(expectedRow.getNumber(), createdRow.getNumber());
        Assertions.assertEquals(expectedRow.getSeats().size(), createdRow.getSeats().size());
        Assertions.assertTrue(createdRow.getSeats().contains(seat1));
        Assertions.assertTrue(createdRow.getSeats().contains(seat2));
    }
}

