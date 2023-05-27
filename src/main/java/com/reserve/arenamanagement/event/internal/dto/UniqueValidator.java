package com.reserve.arenamanagement.event.internal.dto;

import com.reserve.arenamanagement.event.internal.repository.EventRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

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
        return !eventRepository.findByName(value).isEmpty();
    }
}