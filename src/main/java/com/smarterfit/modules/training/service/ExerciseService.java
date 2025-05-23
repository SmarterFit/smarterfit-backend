package com.smarterfit.modules.training.service;

import com.smarterfit.modules.training.dto.request.ExerciseRequestDTO;
import com.smarterfit.modules.training.dto.response.ExerciseResponseDTO;
import com.smarterfit.modules.training.entity.Exercise;
import com.smarterfit.modules.training.mapper.ExerciseMapper;
import com.smarterfit.modules.training.repository.ExerciseRepository;
import com.smarterfit.modules.training.validation.ExerciseValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ExerciseService {
     private final ExerciseRepository exerciseRepository;
     private final ExerciseValidation exerciseValidation;

    public ExerciseService(ExerciseRepository exerciseRepository,
                           ExerciseValidation exerciseValidation) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseValidation = exerciseValidation;
    }


    @Transactional
    public ExerciseResponseDTO createExercise(ExerciseRequestDTO exerciseRequestDTO) {
       exerciseValidation.existsExerciseByName(exerciseRequestDTO.getName());

        Exercise exercise = ExerciseMapper.toEntity(exerciseRequestDTO);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return ExerciseMapper.toResponse(savedExercise);
    }

    @Transactional(readOnly = true)
    public ExerciseResponseDTO getExerciseByName(String name) {
        Exercise exercise = exerciseValidation.validateExerciseByName(name);
        return ExerciseMapper.toResponse(exercise);
    }

    @Transactional(readOnly = true)
    public ExerciseResponseDTO getExerciseById(UUID id) {
        Exercise exercise = exerciseValidation.validateExerciseById(id);
        return ExerciseMapper.toResponse(exercise);
    }

    @Transactional(readOnly = true)
    public Page<ExerciseResponseDTO> getAllExercises(Pageable pageable) {
        return exerciseRepository.findAll(pageable)
                .map(ExerciseMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ExerciseResponseDTO> getAllExercisesByName(String name, Pageable pageable) {
        return exerciseRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(ExerciseMapper::toResponse);
    }

    @Transactional
    public ExerciseResponseDTO updateExercise(UUID id, ExerciseRequestDTO exerciseRequestDTO) {
        Exercise exercise = exerciseValidation.validateExerciseById(id);

        Exercise updateExercise = ExerciseMapper.toEntity(exerciseRequestDTO, exercise);
        Exercise updatedExercise = exerciseRepository.save(updateExercise);
        return ExerciseMapper.toResponse(updatedExercise);
    }

    @Transactional
    public void deleteExercise(UUID id) {
        Exercise exercise = exerciseValidation.validateExerciseById(id);
        exerciseRepository.delete(exercise);
    }



}
