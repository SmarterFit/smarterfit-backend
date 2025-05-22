package com.smarterfit.modules.training.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.training.dto.request.TrainingGoalRequestDTO;
import com.smarterfit.modules.training.dto.response.TrainingGoalResponseDTO;
import com.smarterfit.modules.training.entity.TrainingGoal;

public class TrainingGoalMapper {

    private TrainingGoalMapper() {
        // Private constructor to prevent instantiation
    }

    public static TrainingGoal toEntity(TrainingGoalRequestDTO dto) {
        return toEntity(dto, new TrainingGoal());
    }

    public static TrainingGoal toEntity(TrainingGoalRequestDTO dto, TrainingGoal trainingGoal) {
        if (trainingGoal == null) {
            throw new ResourceNotFoundException("TrainingGoal not found");
        }

        trainingGoal = GenericMapper.map(dto, trainingGoal);

        return trainingGoal;
    }

    public static TrainingGoalResponseDTO toResponse(TrainingGoal trainingGoal) {
        if (trainingGoal == null) {
            throw new ResourceNotFoundException("TrainingGoal not found");
        }

        return GenericMapper.map(trainingGoal, TrainingGoalResponseDTO.class);
    }
}
