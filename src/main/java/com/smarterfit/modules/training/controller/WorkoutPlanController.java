package com.smarterfit.modules.training.controller;


import com.smarterfit.modules.training.dto.request.workoutplans.WorkoutPlanRequestDTO;
import com.smarterfit.modules.training.dto.request.workoutplans.WorkoutPlanUpdateRequestDTO;
import com.smarterfit.modules.training.dto.response.workoutplan.WorkoutPlanExerciseResponseDTO;
import com.smarterfit.modules.training.dto.response.workoutplan.WorkoutPlanResponseDTO;
import com.smarterfit.modules.training.service.WorkoutPlanService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/treinos/plano")
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @PostMapping
    public ResponseEntity<WorkoutPlanResponseDTO> create(
            @Valid @RequestBody WorkoutPlanRequestDTO dto){
        WorkoutPlanResponseDTO created = workoutPlanService.createWorkoutPlan(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<WorkoutPlanResponseDTO> getByUserId(@RequestHeader("X-User-Id") UUID requesterId) {
        WorkoutPlanResponseDTO dto = workoutPlanService.getWorkoutPlanByUserId(requesterId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/exercicios")
    public ResponseEntity<WorkoutPlanExerciseResponseDTO> getFullTraining(
            @RequestHeader("X-User-Id") UUID requesterId) {

        WorkoutPlanExerciseResponseDTO dto = workoutPlanService.getFullTraining(requesterId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<WorkoutPlanResponseDTO> update(
            @RequestHeader("X-User-Id") UUID requesterId,
            @Valid @RequestBody WorkoutPlanUpdateRequestDTO dto) {

        WorkoutPlanResponseDTO updated = workoutPlanService.updateWorkoutPlan(requesterId, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestHeader("X-User-Id") UUID requesterId) {

        workoutPlanService.deleteWorkoutPlan(requesterId);
        return ResponseEntity.noContent().build();
    }
}