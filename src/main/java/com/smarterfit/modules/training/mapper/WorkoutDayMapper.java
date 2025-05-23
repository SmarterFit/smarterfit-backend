package com.smarterfit.modules.training.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.training.dto.request.WorkoutDayRequestDTO;
import com.smarterfit.modules.training.dto.response.WorkoutDayResponseDTO;
import com.smarterfit.modules.training.entity.WorkoutDay;


public class WorkoutDayMapper {

    private WorkoutDayMapper() {
        // Private constructor to prevent instantiation
    }

    public static WorkoutDay toEntity(WorkoutDayRequestDTO dto) {
        return toEntity(dto, new WorkoutDay());
    }

    public static WorkoutDay toEntity(WorkoutDayRequestDTO dto, WorkoutDay workoutDay) {
        if (workoutDay == null) {
            throw new ResourceNotFoundException("WorkoutDay not found");
        }

        workoutDay = GenericMapper.map(dto, workoutDay);

        return workoutDay;
    }

    public static WorkoutDayResponseDTO toResponse(WorkoutDay workoutDay) {
        if (workoutDay == null) {
            throw new ResourceNotFoundException("WorkoutDay not found");
        }

        return GenericMapper.map(workoutDay, WorkoutDayResponseDTO.class);
    }

}
