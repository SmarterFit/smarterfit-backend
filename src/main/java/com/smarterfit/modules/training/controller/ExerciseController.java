package com.smarterfit.modules.training.controller;


import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.security.RequireRole;
import com.smarterfit.modules.training.dto.request.ExerciseRequestDTO;
import com.smarterfit.modules.training.dto.response.ExerciseResponseDTO;
import com.smarterfit.modules.training.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/treinos/exercicio")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @RequireRole(RoleType.EMPLOYEE)
    @PostMapping("/cadastrar")
    public ResponseEntity<ExerciseResponseDTO> create(@Valid @RequestBody ExerciseRequestDTO dto) {
        ExerciseResponseDTO created = exerciseService.createExercise(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<ExerciseResponseDTO>> listAll(Pageable pageable) {
        Page<ExerciseResponseDTO> page = exerciseService.getAllExercises(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> getById(@PathVariable UUID id) {
        ExerciseResponseDTO dto = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ExerciseResponseDTO> getByName(@PathVariable String name) {
        ExerciseResponseDTO dto = exerciseService.getExerciseByName(name);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/buscar/{name}")
    public ResponseEntity<Page<ExerciseResponseDTO>> searchAllByName(
            @PathVariable String name,
            Pageable pageable) {

        Page<ExerciseResponseDTO> page = exerciseService.getAllExercisesByName(name, pageable);
        return ResponseEntity.ok(page);
    }

    @RequireRole(RoleType.EMPLOYEE)
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody ExerciseRequestDTO dto) {
        ExerciseResponseDTO updated = exerciseService.updateExercise(id, dto);
        return ResponseEntity.ok(updated);
    }

    @RequireRole(RoleType.EMPLOYEE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}