package com.smarterfit.util.validation;

import com.smarterfit.exception.ResourceAlreadyExistsException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.ClassGroup;
import com.smarterfit.repository.ClassGroupRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClassGroupValidation {

    private final ClassGroupRepository classGroupRepository;

    public ClassGroupValidation(ClassGroupRepository classGroupRepository) {
        this.classGroupRepository = classGroupRepository;
    }


    public ClassGroup validateClassGroupById(UUID id) {
        return  classGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class group not found."));
    }

    public void validateClassGroupAvailability(String name, UUID currentClassGroupId) {
        classGroupRepository.findByTitle(name).ifPresent(existing -> {
            if (!existing.getId().equals(currentClassGroupId)) {
                throw new ResourceAlreadyExistsException("Class group name is already in use.");
            }
        });

    }
}
