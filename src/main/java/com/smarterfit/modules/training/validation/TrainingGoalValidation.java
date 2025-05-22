package com.smarterfit.modules.training.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.training.entity.TrainingGoal;
import com.smarterfit.modules.training.repository.TrainingGoalRepository;

import java.util.UUID;

public class TrainingGoalValidation {

    private final TrainingGoalRepository trainingGoalRepository;
    
    public TrainingGoalValidation(TrainingGoalRepository trainingGoalRepository) {
        this.trainingGoalRepository = trainingGoalRepository;
    }

    public TrainingGoal validateTrainingGoalById(UUID id) {
        return trainingGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrainingGoal not found"));
    }

    public void existsTrainingGoalById(UUID id) {
        if (trainingGoalRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException("TrainingGoal already exists");
        }
    }

}
