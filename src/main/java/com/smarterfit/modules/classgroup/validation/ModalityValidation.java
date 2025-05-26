package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.classgroup.entity.Modality;
import com.smarterfit.modules.classgroup.repository.ModalityRepository;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ModalityValidation {

    private final ModalityRepository modalityRepository;

    public ModalityValidation(ModalityRepository classGroupRepository) {
        this.modalityRepository = classGroupRepository;
    }

    public Modality validateModalityById(UUID id) {
        return modalityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modality not found."));
    }

    public void existsModalityByName(String name) {
        if (modalityRepository.existsByName(name)) {
            throw new ResourceAlreadyExistsException("Modality already exists");
        }
    }
}
