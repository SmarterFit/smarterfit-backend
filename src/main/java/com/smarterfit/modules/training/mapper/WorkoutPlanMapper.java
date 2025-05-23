package com.smarterfit.modules.training.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.training.dto.request.workoutplans.WorkoutPlanRequestDTO;
import com.smarterfit.modules.training.dto.request.workoutplans.WorkoutPlanUpdateRequestDTO;
import com.smarterfit.modules.training.dto.response.workoutplan.WorkoutPlanResponseDTO;
import com.smarterfit.modules.training.entity.WorkoutPlan;


public class WorkoutPlanMapper {

    private WorkoutPlanMapper() {
        // Private constructor to prevent instantiation
    }

    public static WorkoutPlan toEntity(WorkoutPlanRequestDTO dto) {
        return toEntity(dto, new WorkoutPlan());
    }

    public static WorkoutPlan toEntity(WorkoutPlanRequestDTO dto, WorkoutPlan workoutPlan) {
        if (workoutPlan == null) {
            throw new ResourceNotFoundException("WorkoutPlan not found");
        }

        workoutPlan = GenericMapper.map(dto, workoutPlan);

        return workoutPlan;
    }

    public static WorkoutPlan toEntity(WorkoutPlanUpdateRequestDTO dto, WorkoutPlan workoutPlan) {
        if (workoutPlan == null) {
            throw new ResourceNotFoundException("WorkoutPlan not found");
        }

        workoutPlan = GenericMapper.map(dto, workoutPlan);

        return workoutPlan;
    }



    public static WorkoutPlanResponseDTO toResponse(WorkoutPlan workoutPlan) {
        if (workoutPlan == null) {
            throw new ResourceNotFoundException("WorkoutPlan not found");
        }

        return GenericMapper.map(workoutPlan, WorkoutPlanResponseDTO.class);
    }

}
