package com.smarterfit.util.validation;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.smarterfit.exception.BusinessException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.TrainingGroup.TrainingGroup;
import com.smarterfit.model.TrainingGroup.TrainingGroupUser;
import com.smarterfit.repository.TrainingGroupRepository;

@Component
public class TrainingGroupValidation {
   public final TrainingGroupRepository trainingGroupRepository;

   public TrainingGroupValidation(TrainingGroupRepository trainingGroupRepository) {
      this.trainingGroupRepository = trainingGroupRepository;
   }

   public TrainingGroup findTrainingGroupById(UUID id) {
      return trainingGroupRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Training group not found"));
   }

   public void validateAtLeastOneAdmin(Set<TrainingGroupUser> participants) {
      boolean hasAdmin = participants.stream()
            .anyMatch(participant -> participant.getIsAdmin());

      if (!hasAdmin) {
         throw new BusinessException("The training group must have at least one admin.");
      }
   }

   public Boolean trainingGroupIsActive(TrainingGroup trainingGroup) {
      if (trainingGroup.getStartDate() == null || trainingGroup.getStartDate().isBefore(LocalDate.now())) {
         if (trainingGroup.getEndDate() == null || trainingGroup.getEndDate().isAfter(LocalDate.now())) {
            return true;
         }
      }

      return false;
   }

   public void validateTrainingGroupActive(TrainingGroup trainingGroup) {
      if (!trainingGroupIsActive(trainingGroup)) {
         throw new BusinessException("The training group is not active.");
      }
   }

   public void validateTrainingGroupNotActive(TrainingGroup trainingGroup) {
      if (trainingGroupIsActive(trainingGroup)) {
         throw new BusinessException("The training group is active.");
      }
   }
}
