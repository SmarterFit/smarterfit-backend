package com.smarterfit.modules.training.service;


import com.smarterfit.modules.training.dto.request.WorkoutDayRequestDTO;
import com.smarterfit.modules.training.dto.response.workoutday.WorkoutDayResponseDTO;
import com.smarterfit.modules.training.entity.WorkoutDay;
import com.smarterfit.modules.training.mapper.WorkoutDayMapper;
import com.smarterfit.modules.training.repository.WorkoutDayRepository;
import com.smarterfit.modules.training.validation.WorkoutDayValidation;
import com.smarterfit.modules.training.validation.WorkoutPlanValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WorkoutDayService {

    private final WorkoutDayRepository workoutDayRepository;
    private final WorkoutDayValidation workoutDayValidation;
    private final WorkoutPlanValidation workoutPlanValidation;

    public WorkoutDayService(WorkoutDayRepository workoutDayRepository,
                             WorkoutDayValidation workoutDayValidation,
                             WorkoutPlanValidation workoutPlanValidation) {
        this.workoutDayRepository = workoutDayRepository;
        this.workoutDayValidation = workoutDayValidation;
        this.workoutPlanValidation = workoutPlanValidation;
    }


    @Transactional
    public WorkoutDayResponseDTO createWorkoutDay(WorkoutDayRequestDTO dto) {
        workoutPlanValidation.existsWorkoutPlanById(dto.getWorkoutPlanId());
        workoutDayValidation.existsWorkoutByDayOfWeek(dto.getDayOfWeek().toString());

        WorkoutDay day = WorkoutDayMapper.toEntity(dto);
        WorkoutDay saved = workoutDayRepository.save(day);
        return WorkoutDayMapper.toResponse(saved);
    }


    @Transactional(readOnly = true)
    public WorkoutDayResponseDTO getWorkoutDayById(UUID id) {
        WorkoutDay day = workoutDayValidation.validateWorkoutDayById(id);
        return WorkoutDayMapper.toResponse(day);
    }

    @Transactional(readOnly = true)
    public List<WorkoutDayResponseDTO> getDaysByPlan(UUID planId) {
        workoutPlanValidation.existsWorkoutPlanById(planId);

        return workoutDayRepository.findAll()
                .stream()
                .map(WorkoutDayMapper::toResponse)
                .toList();
    }


    @Transactional
    public WorkoutDayResponseDTO updateWorkoutDay(UUID id, WorkoutDayRequestDTO dto) {
        WorkoutDay existing = workoutDayValidation.validateWorkoutDayById(id);

        WorkoutDay updated = workoutDayRepository.save( WorkoutDayMapper.toEntity(dto, existing));
        return WorkoutDayMapper.toResponse(updated);
    }

    @Transactional
    public void deleteWorkoutDay(UUID id) {
        WorkoutDay existing = workoutDayValidation.validateWorkoutDayById(id);
        workoutDayRepository.delete(existing);
    }
}