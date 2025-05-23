package com.smarterfit.modules.training.controller;


import com.smarterfit.modules.training.dto.request.WorkoutDayRequestDTO;
import com.smarterfit.modules.training.dto.response.workoutday.WorkoutDayResponseDTO;
import com.smarterfit.modules.training.service.WorkoutDayService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/treinos/dia")
public class WorkoutDayController {

    private final WorkoutDayService workoutDayService;

    public WorkoutDayController(WorkoutDayService workoutDayService) {
        this.workoutDayService = workoutDayService;
    }

    @PostMapping
    public ResponseEntity<WorkoutDayResponseDTO> create(@Valid @RequestBody WorkoutDayRequestDTO dto) {

        WorkoutDayResponseDTO created = workoutDayService.createWorkoutDay(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDayResponseDTO> getById(@PathVariable UUID id) {

        WorkoutDayResponseDTO dto = workoutDayService.getWorkoutDayById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<List<WorkoutDayResponseDTO>> listWorkoutDaysByPlan(@PathVariable UUID planId) {

        List<WorkoutDayResponseDTO> days = workoutDayService.getDaysByPlan(planId);
        return ResponseEntity.ok(days);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDayResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody WorkoutDayRequestDTO dto) {

        WorkoutDayResponseDTO updated = workoutDayService.updateWorkoutDay(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        workoutDayService.deleteWorkoutDay(id);
        return ResponseEntity.noContent().build();
    }
}