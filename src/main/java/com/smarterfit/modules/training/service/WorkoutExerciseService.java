package com.smarterfit.modules.training.service;

import com.smarterfit.modules.training.dto.request.WorkoutExerciseRequestDTO;
import com.smarterfit.modules.training.dto.response.WorkoutExerciseResponseDTO;
import com.smarterfit.modules.training.entity.WorkoutExercise;
import com.smarterfit.modules.training.mapper.WorkoutExerciseMapper;
import com.smarterfit.modules.training.repository.WorkoutExercisesRepository;
import com.smarterfit.modules.training.validation.ExerciseValidation;
import com.smarterfit.modules.training.validation.WorkoutDayValidation;
import com.smarterfit.modules.training.validation.WorkoutExerciseValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkoutExerciseService {

    private final WorkoutExercisesRepository workoutExerciseRepository;
    private final WorkoutExerciseValidation workoutExerciseValidation;
    private final WorkoutDayValidation workoutDayValidation;
    private final ExerciseValidation exerciseValidation;

    public WorkoutExerciseService(WorkoutExercisesRepository workoutExerciseRepository,
                                  WorkoutExerciseValidation workoutExerciseValidation,
                                  WorkoutDayValidation workoutDayValidation,
                                  ExerciseValidation exerciseValidation) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutExerciseValidation = workoutExerciseValidation;
        this.workoutDayValidation = workoutDayValidation;
        this.exerciseValidation = exerciseValidation;
    }

    @Transactional
    public WorkoutExerciseResponseDTO createWorkoutExercise(WorkoutExerciseRequestDTO dto) {
        workoutDayValidation.validateWorkoutDayById(dto.getWorkoutDayId());
        exerciseValidation.validateExerciseById(dto.getExerciseId());

        WorkoutExercise entity = WorkoutExerciseMapper.toEntity(dto);
        WorkoutExercise saved = workoutExerciseRepository.save(entity);
        return WorkoutExerciseMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<WorkoutExerciseResponseDTO> listByWorkoutDay(UUID workoutDayId) {
        workoutDayValidation.validateWorkoutDayById(workoutDayId);

        return workoutExerciseRepository
                .findByWorkoutDayId(workoutDayId)
                .stream()
                .map(WorkoutExerciseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public WorkoutExerciseResponseDTO updateWorkoutExercise(UUID id, WorkoutExerciseRequestDTO dto) {
        WorkoutExercise existing = workoutExerciseValidation.validateWorkoutExercisesById(id);

        workoutDayValidation.validateWorkoutDayById(dto.getWorkoutDayId());
        exerciseValidation.validateExerciseById(dto.getExerciseId());


        WorkoutExercise updated = workoutExerciseRepository.save( WorkoutExerciseMapper.toEntity(dto, existing));
        return WorkoutExerciseMapper.toResponse(updated);
    }

    @Transactional
    public void deleteWorkoutExercise(UUID id) {
        WorkoutExercise existing = workoutExerciseValidation.validateWorkoutExercisesById(id);
        workoutExerciseRepository.delete(existing);
    }
}