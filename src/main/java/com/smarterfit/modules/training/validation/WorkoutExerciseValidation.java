package com.smarterfit.modules.training.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.training.entity.WorkoutExercise;
import com.smarterfit.modules.training.repository.WorkoutExercisesRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WorkoutExerciseValidation {

    private final WorkoutExercisesRepository workoutExercisesRepository;

    public WorkoutExerciseValidation(WorkoutExercisesRepository workoutExercisesRepository) {
        this.workoutExercisesRepository = workoutExercisesRepository;
    }

    public WorkoutExercise validateWorkoutExercisesById(UUID id) {
        return workoutExercisesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutExercises not found"));
    }

    public void existsWorkoutExercisesById(UUID id) {
        if (workoutExercisesRepository.existsById(id)) {
            throw new ResourceAlreadyExistsException("WorkoutExercises already exists");
        }
    }
}
