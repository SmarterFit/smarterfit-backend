package com.smarterfit.modules.training.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.training.dto.request.EquipmentRequestDTO;
import com.smarterfit.modules.training.dto.response.EquipmentResponseDTO;
import com.smarterfit.modules.training.entity.Equipment;


public class WorkoutExercisesMapper {

    private WorkoutExercisesMapper() {
        // Private constructor to prevent instantiation
    }

    public static Equipment toEntity(EquipmentRequestDTO dto) {
        return toEntity(dto, new Equipment());
    }

    public static Equipment toEntity(EquipmentRequestDTO dto, Equipment workoutExercise) {
        if (workoutExercise == null) {
            throw new ResourceNotFoundException("Equipment not found");
        }

        workoutExercise = GenericMapper.map(dto, workoutExercise);

        return workoutExercise;
    }

    public static EquipmentResponseDTO toResponse(Equipment workoutExercise) {
        if (workoutExercise == null) {
            throw new ResourceNotFoundException("Equipment not found");
        }

        return GenericMapper.map(workoutExercise, EquipmentResponseDTO.class);
    }

}
