package com.smarterfit.util.validation;

import com.smarterfit.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

    public <T> T validateNotNull(T entity, String message) {
        if (entity == null) {
            throw new ResourceNotFoundException(message);
        }
        return entity;
    }
}
