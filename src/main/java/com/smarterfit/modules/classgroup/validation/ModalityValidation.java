package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.classgroup.entity.Modality;
import com.smarterfit.modules.classgroup.repository.ModalityRepository;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ModalityValidation {

    private final ModalityRepository classGroupRepository;

    public ModalityValidation(ModalityRepository classGroupRepository) {
        this.classGroupRepository = classGroupRepository;
    }

    public Modality validateModalityById(UUID id) {
        return classGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modality not found."));
    }

    public void existsModalityByName(String name) {
        if (classGroupRepository.existsByName(name)) {
            throw new ResourceNotFoundException("Modality already exists");
        }
    }
}
