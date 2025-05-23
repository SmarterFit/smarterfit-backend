package com.smarterfit.modules.training.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.training.dto.request.ExerciseRequestDTO;
import com.smarterfit.modules.training.dto.response.ExerciseResponseDTO;
import com.smarterfit.modules.training.entity.Exercise;


public class ExerciseMapper {

    private ExerciseMapper() {
        // Private constructor to prevent instantiation
    }

    public static Exercise toEntity(ExerciseRequestDTO dto) {
        return toEntity(dto, new Exercise());
    }

    public static Exercise toEntity(ExerciseRequestDTO dto, Exercise exercise) {
        if (exercise == null) {
            throw new ResourceNotFoundException("Exercise not found");
        }

        exercise = GenericMapper.map(dto, exercise);

        return exercise;
    }

    public static ExerciseResponseDTO toResponse(Exercise exercise) {
        if (exercise == null) {
            throw new ResourceNotFoundException("Exercise not found");
        }

        return GenericMapper.map(exercise, ExerciseResponseDTO.class);
    }

}
