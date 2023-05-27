package com.reserve.arenamanagement.sector.internal;

import com.reserve.arenamanagement.arena.ArenaService;
import com.reserve.arenamanagement.sector.SectorService;
import com.reserve.arenamanagement.sector.internal.dto.CreateRowRequest;
import com.reserve.arenamanagement.sector.internal.dto.CreateSectorRequest;
import com.reserve.arenamanagement.sector.internal.dto.SectorDto;
import com.reserve.arenamanagement.sector.internal.entity.Row;
import com.reserve.arenamanagement.sector.internal.entity.Seat;
import com.reserve.arenamanagement.sector.internal.entity.Sector;
import com.reserve.arenamanagement.sector.internal.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SectorServiceTest {

    private static final String ARENA_ID = "arenaId";
    SectorService sectorService;
    private final Sector sector = getSector();

    private final CreateSectorRequest createSectorRequest = generateSectorRequest();

    @Mock
    private ArenaService arenaService;

    @Mock
    private SectorDto sectorDto;


    public SectorServiceTest() {
        MockitoAnnotations.openMocks(this);
        sectorService = new SectorServiceImpl(arenaService);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void addSector() {
        when(sectorDto.getName()).thenReturn("A");
        when(arenaService.addSector(any(), any())).thenReturn(sectorDto);

        SectorDto sectorDto = sectorService.addSector(ARENA_ID, createSectorRequest);
    }

    private CreateSectorRequest generateSectorRequest() {
        CreateSectorRequest request = new CreateSectorRequest();
        request.setName("A");
        request.setCreateRowsRequest(generateRowRequest());
        return request;
    }

    private List<CreateRowRequest> generateRowRequest() {
        CreateRowRequest row1 = new CreateRowRequest();
        row1.setNumber(1L);
        row1.setSeats(10L);

        CreateRowRequest row2 = new CreateRowRequest();
        row2.setNumber(1L);
        row2.setSeats(10L);

        CreateRowRequest row3 = new CreateRowRequest();
        row3.setNumber(1L);
        row3.setSeats(10L);

        return Arrays.asList(row1, row2, row3);
    }

    private Sector getSector() {
        Sector sector1 = new Sector();
        sector1.setName("A");
        sector1.setRows(generateRows());
        return sector1;
    }

    private List<Row> generateRows() {
        Row row1 = new Row(1L, generateSeat(10L));
        Row row2 = new Row(2L, generateSeat(10L));
        Row row3 = new Row(3L, generateSeat(10L));

        return Arrays.asList(row1, row2, row3);
    }

    private List<Seat> generateSeat(long seats) {
        final List<Seat> seatList = new ArrayList<>();
        for (int i = 1 ; i < seats ; i++) {
            seatList.add(new Seat((long) i, Status.AVAILABLE));
        }
        return seatList;
    }

}