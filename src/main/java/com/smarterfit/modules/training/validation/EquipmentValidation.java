package com.smarterfit.modules.training.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.training.entity.Equipment;
import com.smarterfit.modules.training.repository.EquipmentRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EquipmentValidation {
    
    private final EquipmentRepository equipmentRepository;
    
    public EquipmentValidation(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }
    
    public Equipment validateEquipmentById(UUID id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found"));
    }

    public void existsEquipmentByName(String name) {
        if (equipmentRepository.existsByName(name)) {
            throw new ResourceAlreadyExistsException("Equipment already exists");
        }
    }
}
