package com.smarterfit.modules.traininggroup.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.traininggroup.dto.request.SearchTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.dto.request.CreateTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.dto.request.UpdateTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupResponseDTO;
import com.smarterfit.modules.traininggroup.service.TrainingGroupService;

@RestController
@RequestMapping("/grupos-de-treinamento")
@CrossOrigin("*")
public class TrainingGroupController {
   private final TrainingGroupService trainingGroupService;

   @Autowired
   public TrainingGroupController(TrainingGroupService trainingGroupService) {
      this.trainingGroupService = trainingGroupService;
   }

   @PostMapping
   public ResponseEntity<TrainingGroupResponseDTO> createTrainingGroup(
         @RequestBody CreateTrainingGroupRequestDTO requestDTO) {
      TrainingGroupResponseDTO trainingGroupResponseDTO = trainingGroupService.createTrainingGroup(requestDTO);
      return ResponseEntity.status(201).body(trainingGroupResponseDTO);
   }

   @GetMapping("/{id}")
   public ResponseEntity<TrainingGroupResponseDTO> getTrainingGroupById(@PathVariable("id") UUID id) {
      return ResponseEntity.ok(trainingGroupService.getTrainingGroupById(id));
   }

   @GetMapping
   public ResponseEntity<List<TrainingGroupResponseDTO>> getAllTrainingGroups() {
      return ResponseEntity.ok(trainingGroupService.getAllTrainingGroups());
   }

   @GetMapping("/buscar")
   public ResponseEntity<Page<TrainingGroupResponseDTO>> searchTrainingGroups(
         @ModelAttribute SearchTrainingGroupRequestDTO requestDTO,
         Pageable pageable) {
      return ResponseEntity.ok(trainingGroupService.searchTrainingGroups(requestDTO, pageable));
   }

   @PutMapping("/{id}")
   public ResponseEntity<TrainingGroupResponseDTO> updateTrainingGroup(
         @PathVariable("id") UUID id,
         @RequestBody UpdateTrainingGroupRequestDTO requestDTO) {
      return ResponseEntity.ok(trainingGroupService.updateTrainingGroup(id, requestDTO));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteTrainingGroup(@PathVariable("id") UUID id) {
      trainingGroupService.deleteTrainingGroup(id);
      return ResponseEntity.noContent().build();
   }

   @PatchMapping("/{id}/ativar")
   public ResponseEntity<TrainingGroupResponseDTO> activateTrainingGroup(@PathVariable("id") UUID id) {
      return ResponseEntity.ok(trainingGroupService.activateTrainingGroup(id));
   }

   @PatchMapping("/{id}/finalizar")
   public ResponseEntity<TrainingGroupResponseDTO> finishTrainingGroup(@PathVariable("id") UUID id) {
      return ResponseEntity.ok(trainingGroupService.finishTrainingGroup(id));
   }

   @PatchMapping("/{id}/reiniciar")
   public ResponseEntity<TrainingGroupResponseDTO> restartTrainingGroup(@PathVariable("id") UUID id) {
      return ResponseEntity.ok(trainingGroupService.restartTrainingGroup(id));
   }
}
