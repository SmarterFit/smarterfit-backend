package com.smarterfit.modules.training.service;


import com.smarterfit.modules.training.dto.request.workoutplans.WorkoutPlanRequestDTO;
import com.smarterfit.modules.training.dto.request.workoutplans.WorkoutPlanUpdateRequestDTO;
import com.smarterfit.modules.training.dto.response.workoutplan.WorkoutPlanResponseDTO;
import com.smarterfit.modules.training.entity.TrainingGoal;
import com.smarterfit.modules.training.entity.WorkoutPlan;
import com.smarterfit.modules.training.mapper.WorkoutPlanMapper;
import com.smarterfit.modules.training.repository.WorkoutPlanRepository;
import com.smarterfit.modules.training.validation.TrainingGoalValidation;
import com.smarterfit.modules.training.validation.WorkoutPlanValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutPlanValidation workoutPlanValidation;
    private final TrainingGoalValidation trainingGoalValidation;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository,
                              WorkoutPlanValidation workoutPlanValidation,
                              TrainingGoalValidation trainingGoalValidation) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.workoutPlanValidation = workoutPlanValidation;
        this.trainingGoalValidation = trainingGoalValidation;
    }


    @Transactional
    public WorkoutPlanResponseDTO createWorkoutPlan(WorkoutPlanRequestDTO dto) {
        workoutPlanValidation.existsWorkoutPlanById(dto.getTrainingGoalId());
        TrainingGoal trainingGoal = trainingGoalValidation.validateTrainingGoalById(dto.getTrainingGoalId());

        WorkoutPlan plan = WorkoutPlanMapper.toEntity(dto, trainingGoal);

        WorkoutPlan saved = workoutPlanRepository.save(plan);
        return WorkoutPlanMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public WorkoutPlanResponseDTO getWorkoutPlanByUserId(UUID userId) {
        WorkoutPlan plan = workoutPlanValidation.validateWorkoutPlanById(userId);
        return WorkoutPlanMapper.toResponse(plan);
    }


    @Transactional
    public WorkoutPlanResponseDTO updateWorkoutPlan(UUID userId, WorkoutPlanUpdateRequestDTO dto) {
        WorkoutPlan plan = workoutPlanValidation.validateWorkoutPlanById(userId);

        WorkoutPlan updated = workoutPlanRepository.save(WorkoutPlanMapper.toEntity(dto, plan));
        return WorkoutPlanMapper.toResponse(updated);
    }

    @Transactional
    public void deleteWorkoutPlan(UUID userId) {
        WorkoutPlan plan = workoutPlanValidation.validateWorkoutPlanById(userId);
        workoutPlanRepository.delete(plan);
    }
}