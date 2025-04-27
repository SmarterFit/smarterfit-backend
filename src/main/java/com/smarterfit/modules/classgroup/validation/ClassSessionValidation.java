package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.classgroup.entity.ClassSession;
import com.smarterfit.modules.classgroup.repository.ClassSessionRepository;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClassSessionValidation {

    private final ClassSessionRepository classSessionRepository;

    public ClassSessionValidation(ClassSessionRepository classSessionRepository) {
        this.classSessionRepository = classSessionRepository;
    }

    public ClassSession validateClassSessionById(UUID id) {
        return classSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class group not found."));
    }

    public void validateClassSessionExists(UUID id) {
        if (classSessionRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException("Class session already exists");
        }
    }
}
