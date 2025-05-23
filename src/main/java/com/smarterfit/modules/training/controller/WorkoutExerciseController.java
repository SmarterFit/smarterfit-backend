package com.smarterfit.modules.training.controller;

import com.smarterfit.modules.training.dto.request.WorkoutExerciseRequestDTO;
import com.smarterfit.modules.training.dto.response.WorkoutExerciseResponseDTO;
import com.smarterfit.modules.training.service.WorkoutExerciseService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/treinos/plano/exercicios")
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutExerciseController(WorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @PostMapping
    public ResponseEntity<WorkoutExerciseResponseDTO> create(
            @Valid @RequestBody WorkoutExerciseRequestDTO dto) {

        WorkoutExerciseResponseDTO created = workoutExerciseService.createWorkoutExercise(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/day")
    public ResponseEntity<List<WorkoutExerciseResponseDTO>> listByDay(@PathVariable UUID workoutDayId) {

        List<WorkoutExerciseResponseDTO> list = workoutExerciseService.listByWorkoutDay(workoutDayId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutExerciseResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody WorkoutExerciseRequestDTO dto) {

        WorkoutExerciseResponseDTO updated = workoutExerciseService.updateWorkoutExercise(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        workoutExerciseService.deleteWorkoutExercise(id);
        return ResponseEntity.noContent().build();
    }
}