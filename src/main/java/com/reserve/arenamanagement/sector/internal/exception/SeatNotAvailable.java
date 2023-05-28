package com.reserve.arenamanagement.sector.internal.exception;

public class SeatNotAvailable extends RuntimeException {
    public SeatNotAvailable(String message) {
        super(message);
    }
}
