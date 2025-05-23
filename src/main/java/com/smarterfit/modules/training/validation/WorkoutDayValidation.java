package com.smarterfit.modules.training.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.training.entity.WorkoutDay;
import com.smarterfit.modules.training.repository.WorkoutDayRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WorkoutDayValidation {

    private final WorkoutDayRepository workoutDayRepository;

    public WorkoutDayValidation(WorkoutDayRepository workoutDayRepository) {
        this.workoutDayRepository = workoutDayRepository;
    }
    
    public WorkoutDay validateWorkoutDayById(UUID id) {
        return workoutDayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutDay not found"));
    }

    public void existsWorkoutDayById(UUID id) {
        if (workoutDayRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException("WorkoutDay already exists");
        }
    }
}
