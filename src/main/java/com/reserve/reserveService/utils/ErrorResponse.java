package com.reserve.reserveService.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ErrorResponse {
    private String errorMessage;
    private int errorCode;
}