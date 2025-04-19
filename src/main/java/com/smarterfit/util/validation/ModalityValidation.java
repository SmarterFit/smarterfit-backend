package com.smarterfit.util.validation;

import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Modality;
import com.smarterfit.repository.ModalityRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ModalityValidation {

    private final ModalityRepository classGroupRepository;

    public ModalityValidation(ModalityRepository classGroupRepository) {
        this.classGroupRepository = classGroupRepository;
    }


    public Modality validateModalityById(UUID id) {
        return  classGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modality not found."));
    }

}
