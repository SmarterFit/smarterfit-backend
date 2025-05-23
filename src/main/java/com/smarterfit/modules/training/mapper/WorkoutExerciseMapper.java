package com.smarterfit.modules.training.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.training.dto.request.WorkoutExerciseRequestDTO;
import com.smarterfit.modules.training.dto.response.WorkoutExerciseResponseDTO;
import com.smarterfit.modules.training.entity.WorkoutExercise;


public class WorkoutExerciseMapper {

    private WorkoutExerciseMapper() {
        // Private constructor to prevent instantiation
    }

    public static WorkoutExercise toEntity(WorkoutExerciseRequestDTO dto) {
        return toEntity(dto, new WorkoutExercise());
    }

    public static WorkoutExercise toEntity(WorkoutExerciseRequestDTO dto, WorkoutExercise workoutExercise) {
        if (workoutExercise == null) {
            throw new ResourceNotFoundException("WorkoutExercise not found");
        }

        workoutExercise = GenericMapper.map(dto, workoutExercise);

        return workoutExercise;
    }

    public static WorkoutExerciseResponseDTO toResponse(WorkoutExercise workoutExercise) {
        if (workoutExercise == null) {
            throw new ResourceNotFoundException("WorkoutExercise not found");
        }

        return GenericMapper.map(workoutExercise, WorkoutExerciseResponseDTO.class);
    }

}
