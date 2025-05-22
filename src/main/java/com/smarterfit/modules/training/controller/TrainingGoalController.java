package com.smarterfit.modules.training.controller;

import com.smarterfit.modules.training.dto.request.TrainingGoalRequestDTO;
import com.smarterfit.modules.training.dto.response.TrainingGoalResponseDTO;
import com.smarterfit.modules.training.service.TrainingGoalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("treinos/objetivos")
public class TrainingGoalController {


    private final TrainingGoalService trainingGoalService;

    public TrainingGoalController(TrainingGoalService trainingGoalService) {
        this.trainingGoalService = trainingGoalService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<TrainingGoalResponseDTO> create(@Valid @RequestBody TrainingGoalRequestDTO requestDTO,
                                                          @RequestHeader("X-User-Id") UUID requesterId) {
        TrainingGoalResponseDTO response = trainingGoalService.createTrainingGoal(requestDTO, requesterId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<TrainingGoalResponseDTO> getByUserId(@RequestHeader("X-User-Id") UUID requesterId) {
        TrainingGoalResponseDTO response = trainingGoalService.getTrainingGoalByUserId(requesterId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/atualizar")
    public ResponseEntity<TrainingGoalResponseDTO> update(@Valid @RequestBody TrainingGoalRequestDTO requestDTO,
                                                          @RequestHeader("X-User-Id") UUID requesterId) {
        TrainingGoalResponseDTO response = trainingGoalService.updateTrainingGoal(requesterId, requestDTO);
        return ResponseEntity.ok(response);
    }
}
