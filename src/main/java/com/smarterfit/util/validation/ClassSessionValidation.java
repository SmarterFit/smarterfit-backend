package com.smarterfit.util.validation;

import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.ClassSession;
import com.smarterfit.repository.ClassSessionRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClassSessionValidation {
    
    private final ClassSessionRepository classSessionRepository;
    
    public ClassSessionValidation(ClassSessionRepository classSessionRepository) {
        this.classSessionRepository = classSessionRepository;
    }
    
    
    public ClassSession validateClassSessionById(UUID id) {
        return  classSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class group not found."));
    }
}
