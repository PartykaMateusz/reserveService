package com.reserve.reserveService.event.internal.dto;

import com.reserve.reserveService.event.internal.Event;
import com.reserve.reserveService.event.internal.EventRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @Autowired
    private EventRepository eventRepository;

    private String message;

    @Override
    public void initialize(Unique constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return value != null && !isExist(value);
    }

    private boolean isExist(String value) {
        List<Event> events = eventRepository.findAll();
        return !eventRepository.findByName(value).isEmpty();
    }
}