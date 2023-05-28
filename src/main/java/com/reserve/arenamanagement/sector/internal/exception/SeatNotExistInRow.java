package com.reserve.arenamanagement.sector.internal.exception;

import com.reserve.arenamanagement.sector.internal.entity.Seat;

public class SeatNotExistInRow extends RuntimeException {
    public SeatNotExistInRow(String message) {
        super(message);
    }
}
