package com.smarterfit.modules.training.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.training.entity.Exercise;
import com.smarterfit.modules.training.repository.ExerciseRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ExerciseValidation {
    
    private final ExerciseRepository exerciseRepository;
    
    public ExerciseValidation(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }
    
    public Exercise validateExerciseById(UUID id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));
    }

    public Exercise validateExerciseByName(String name) {
        return exerciseRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));
    }


    public void existsExerciseByName(String name) {
        if (exerciseRepository.existsByName(name)) {
            throw new ResourceAlreadyExistsException("Exercise already exists");
        }
    }


}
