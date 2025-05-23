package com.smarterfit.modules.training.service;


import com.smarterfit.modules.training.dto.request.workoutplans.WorkoutPlanRequestDTO;
import com.smarterfit.modules.training.dto.request.workoutplans.WorkoutPlanUpdateRequestDTO;
import com.smarterfit.modules.training.dto.response.WorkoutExerciseResponseDTO;
import com.smarterfit.modules.training.dto.response.workoutday.WorkoutDayAllExerciseResponseDTO;
import com.smarterfit.modules.training.dto.response.workoutday.WorkoutDayResponseDTO;
import com.smarterfit.modules.training.dto.response.workoutplan.WorkoutPlanExerciseResponseDTO;
import com.smarterfit.modules.training.dto.response.workoutplan.WorkoutPlanResponseDTO;
import com.smarterfit.modules.training.entity.WorkoutPlan;
import com.smarterfit.modules.training.mapper.WorkoutPlanMapper;
import com.smarterfit.modules.training.repository.WorkoutPlanRepository;
import com.smarterfit.modules.training.validation.WorkoutPlanValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutPlanValidation workoutPlanValidation;
    private final WorkoutDayService workoutDayService;
    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository,
                              WorkoutPlanValidation workoutPlanValidation,
                              WorkoutDayService workoutDayService,
                              WorkoutExerciseService workoutExerciseService) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.workoutPlanValidation = workoutPlanValidation;
        this.workoutDayService = workoutDayService;
        this.workoutExerciseService = workoutExerciseService;
    }


    @Transactional
    public WorkoutPlanResponseDTO createWorkoutPlan(WorkoutPlanRequestDTO dto) {
        workoutPlanValidation.existsWorkoutPlanById(dto.getTrainingGoalId());

        WorkoutPlan plan = WorkoutPlanMapper.toEntity(dto);

        WorkoutPlan saved = workoutPlanRepository.save(plan);
        return WorkoutPlanMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public WorkoutPlanResponseDTO getWorkoutPlanByUserId(UUID userId) {
        WorkoutPlan plan = workoutPlanValidation.validateWorkoutPlanById(userId);
        return WorkoutPlanMapper.toResponse(plan);
    }



    @Transactional(readOnly = true)
    public WorkoutPlanExerciseResponseDTO getFullTraining(UUID planId) {
        WorkoutPlan plan = workoutPlanValidation.validateWorkoutPlanById(planId);

        List<WorkoutDayResponseDTO> days = workoutDayService.getDaysByPlan(planId);

        WorkoutPlanExerciseResponseDTO fullDTO = new WorkoutPlanExerciseResponseDTO();
        fullDTO.setPlanId(planId);
        fullDTO.setTitle(plan.getTitle());

        for (var day : days) {
            List<WorkoutExerciseResponseDTO> exercises = workoutExerciseService.listByWorkoutDay(day.getId());

            WorkoutDayAllExerciseResponseDTO workoutDayAllExerciseResponseDTO = new WorkoutDayAllExerciseResponseDTO();
            workoutDayAllExerciseResponseDTO.setExercises(exercises);
            fullDTO.addDays(workoutDayAllExerciseResponseDTO);
        }
        return fullDTO;
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