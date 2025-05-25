package com.smarterfit.modules.traininggroup.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupResponseDTO;
import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupUserResponseDTO;
import com.smarterfit.modules.traininggroup.service.TrainingGroupUserService;

@RestController
@RequestMapping("/grupos-de-treinamento/usuarios")
@CrossOrigin
public class TrainingGroupUserController {
   private final TrainingGroupUserService trainingGroupUserService;

   @Autowired
   public TrainingGroupUserController(TrainingGroupUserService trainingGroupUserService) {
      this.trainingGroupUserService = trainingGroupUserService;
   }

   @PostMapping("/{groupId}/usuario/{userId}/adicionar")
   public ResponseEntity<TrainingGroupUserResponseDTO> addUserToTrainingGroup(@PathVariable("groupId") UUID groupId,
         @PathVariable("userId") UUID userId) {
      TrainingGroupUserResponseDTO response = trainingGroupUserService.addUserToTrainingGroup(groupId, userId);
      return ResponseEntity.status(201).body(response);
   }

   @DeleteMapping("/{groupId}/usuario/{userId}/remover")
   public ResponseEntity<Void> removeUserFromTrainingGroup(@PathVariable("groupId") UUID groupId,
         @PathVariable("userId") UUID userId) {
      trainingGroupUserService.removeUserFromTrainingGroup(groupId, userId);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/{groupId}/usuario/{userId}")
   public ResponseEntity<TrainingGroupUserResponseDTO> getTrainingGroupUser(@PathVariable("groupId") UUID groupId,
         @PathVariable("userId") UUID userId) {
      TrainingGroupUserResponseDTO response = trainingGroupUserService.getTrainingGroupUser(groupId, userId);
      return ResponseEntity.ok(response);
   }

   @GetMapping
   public ResponseEntity<List<TrainingGroupUserResponseDTO>> getAllTrainingGroupUser() {
      return ResponseEntity.ok(trainingGroupUserService.getAllTrainingGroupUser());
   }

   @GetMapping("/grupo/{groupId}")
   public ResponseEntity<List<TrainingGroupUserResponseDTO>> getAllUsersByTrainingGroupId(
         @PathVariable("groupId") UUID groupId) {
      return ResponseEntity.ok(trainingGroupUserService.getAllUsersByTrainingGroupId(groupId));
   }

   @GetMapping("/usuario/{userId}")
   public ResponseEntity<List<TrainingGroupResponseDTO>> getAllTrainingGroupsByUserId(
         @PathVariable("userId") UUID userId) {
      return ResponseEntity.ok(trainingGroupUserService.getAllTrainingGroupsByUserId(userId));
   }

   @GetMapping("/rank/{groupId}")
   public ResponseEntity<List<TrainingGroupUserResponseDTO>> getRankByTrainingGroupId(
         @PathVariable("groupId") UUID groupId) {
      return ResponseEntity.ok(trainingGroupUserService.getRankByTrainingGroupId(groupId));
   }

   @PatchMapping("/{groupId}/usuario/{userId}/admin")
   public ResponseEntity<TrainingGroupUserResponseDTO> setUserAsAdmin(@PathVariable("groupId") UUID groupId,
         @PathVariable("userId") UUID userId) {
      TrainingGroupUserResponseDTO response = trainingGroupUserService.setUserAsAdmin(groupId, userId);
      return ResponseEntity.ok(response);
   }

   @PatchMapping("/{groupId}/usuario/{userId}/admin/remover")
   public ResponseEntity<TrainingGroupUserResponseDTO> removeUserAsAdmin(@PathVariable("groupId") UUID groupId,
         @PathVariable("userId") UUID userId) {
      TrainingGroupUserResponseDTO response = trainingGroupUserService.removeUserAsAdmin(groupId, userId);
      return ResponseEntity.ok(response);
   }
}
