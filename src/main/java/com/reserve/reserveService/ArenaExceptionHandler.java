package com.reserve.reserveService;

import com.reserve.reserveService.arena.internal.exception.ArenaNotFoundException;
import com.reserve.reserveService.event.internal.exception.EventNotFoundException;
import com.reserve.reserveService.utils.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ArenaExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String error = bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst().orElse(null);
        return new ResponseEntity<>(getErrorResponse(error, 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ArenaNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleArenaNotFoundExceptionn(ArenaNotFoundException ex) {
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EventNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEventNotFoundException(EventNotFoundException ex) {
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return new ResponseEntity<>(getErrorResponse(ex.getMessage(), 500), HttpStatus.BAD_REQUEST);
    }
    private static ErrorResponse getErrorResponse(String message, int status) {
        return new ErrorResponse(message, status);
    }

}
