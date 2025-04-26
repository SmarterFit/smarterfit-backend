package com.smarterfit.controller;

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

import com.smarterfit.dto.request.training_group.SearchDTO;
import com.smarterfit.dto.request.training_group.TrainingGroupDTO;
import com.smarterfit.dto.request.training_group.UpdateDTO;
import com.smarterfit.dto.response.training_group.TrainingGroupResponseDTO;
import com.smarterfit.dto.response.training_group.TrainingGroupUserResponseDTO;
import com.smarterfit.service.TrainingGroupService;

@RestController
@RequestMapping("/grupos-treinamento")
@CrossOrigin
public class TrainingGroupController {
   private final TrainingGroupService trainingGroupService;

   @Autowired
   public TrainingGroupController(TrainingGroupService trainingGroupService) {
      this.trainingGroupService = trainingGroupService;
   }

   @PostMapping
   public ResponseEntity<TrainingGroupResponseDTO> createTrainingGroup(
         @RequestBody TrainingGroupDTO trainingGroupDTO) {
      TrainingGroupResponseDTO trainingGroupResponseDTO = trainingGroupService.createTrainingGroup(trainingGroupDTO);
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

   @PutMapping("/{id}")
   public ResponseEntity<TrainingGroupResponseDTO> updateTrainingGroup(
         @PathVariable("id") UUID id,
         @RequestBody UpdateDTO updateDTO) {
      return ResponseEntity.ok(trainingGroupService.updateTrainingGroup(id, updateDTO));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteTrainingGroup(@PathVariable("id") UUID id) {
      trainingGroupService.deleteTrainingGroup(id);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/search")
   public ResponseEntity<Page<TrainingGroupResponseDTO>> searchTrainingGroups(
         @ModelAttribute SearchDTO searchDTO,
         Pageable pageable) {
      return ResponseEntity.ok(trainingGroupService.searchTrainingGroups(searchDTO, pageable));
   }

   @PatchMapping("/{id}/adicionar-usuario/{userId}")
   public ResponseEntity<TrainingGroupUserResponseDTO> addUserToTrainingGroup(
         @PathVariable("id") UUID id,
         @PathVariable("userId") UUID userId) {
      TrainingGroupUserResponseDTO responseDTO = trainingGroupService.addUserToTrainingGroup(id, userId);
      return ResponseEntity.ok(responseDTO);
   }

   @DeleteMapping("/{id}/remover-usuario/{userId}")
   public ResponseEntity<Void> removeUserFromTrainingGroup(
         @PathVariable("id") UUID id,
         @PathVariable("userId") UUID userId) {
      trainingGroupService.removeUserFromTrainingGroup(id, userId);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/{id}/usuarios")
   public ResponseEntity<Page<TrainingGroupUserResponseDTO>> getUsersByTrainingGroupId(
         @PathVariable("id") UUID id,
         Pageable pageable) {
      return ResponseEntity.ok(trainingGroupService.getUsersInTrainingGroup(id, pageable));
   }

   @PatchMapping("/{id}/encerrar")
   public ResponseEntity<TrainingGroupResponseDTO> finishTrainingGroup(@PathVariable("id") UUID id) {
      return ResponseEntity.ok(trainingGroupService.finishTrainingGroup(id));
   }

   @PatchMapping("/{id}/ativar")
   public ResponseEntity<TrainingGroupResponseDTO> activateTrainingGroup(@PathVariable("id") UUID id) {

      return ResponseEntity.ok(trainingGroupService.activateTrainingGroup(id));
   }

   @PatchMapping("/{id}/tornar-admin/{userId}")
   public ResponseEntity<TrainingGroupUserResponseDTO> setUserAsAdmin(@PathVariable("id") UUID id,
         @PathVariable("userId") UUID userId) {
      return ResponseEntity.ok(trainingGroupService.setUserAsAdmin(id, userId));
   }
}
