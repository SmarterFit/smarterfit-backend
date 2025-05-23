package com.smarterfit.modules.training.service;

import com.smarterfit.modules.training.dto.request.TrainingGoalRequestDTO;
import com.smarterfit.modules.training.dto.response.TrainingGoalResponseDTO;
import com.smarterfit.modules.training.entity.TrainingGoal;
import com.smarterfit.modules.training.mapper.TrainingGoalMapper;
import com.smarterfit.modules.training.repository.TrainingGoalRepository;
import com.smarterfit.modules.training.validation.TrainingGoalValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TrainingGoalService {
    private final TrainingGoalRepository trainingGoalRepository;
    private final TrainingGoalValidation trainingGoalValidation;

    public TrainingGoalService(TrainingGoalRepository trainingGoalRepository,
                               TrainingGoalValidation trainingGoalValidation) {
        this.trainingGoalRepository = trainingGoalRepository;
        this.trainingGoalValidation = trainingGoalValidation;
    }

    @Transactional
    public TrainingGoalResponseDTO createTrainingGoal(TrainingGoalRequestDTO requestDTO, UUID userId) {
        trainingGoalValidation.existsTrainingGoalById(userId);

        TrainingGoal trainingGoal = TrainingGoalMapper.toEntity(requestDTO);
        trainingGoalRepository.save(trainingGoal);
        return TrainingGoalMapper.toResponse(trainingGoal);
    }

    @Transactional(readOnly = true)
    public TrainingGoalResponseDTO getTrainingGoalByUserId(UUID userId) {
        TrainingGoal trainingGoal = trainingGoalValidation.validateTrainingGoalById(userId);
        return TrainingGoalMapper.toResponse(trainingGoal);
    }

    @Transactional
    public TrainingGoalResponseDTO updateTrainingGoal(UUID userId, TrainingGoalRequestDTO requestDTO) {
        TrainingGoal trainingGoal = trainingGoalValidation.validateTrainingGoalById(userId);

        trainingGoal = TrainingGoalMapper.toEntity(requestDTO, trainingGoal);
        trainingGoalRepository.save(trainingGoal);
        return TrainingGoalMapper.toResponse(trainingGoal);
    }

}
