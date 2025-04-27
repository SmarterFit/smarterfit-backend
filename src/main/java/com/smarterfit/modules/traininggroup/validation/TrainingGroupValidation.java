package com.smarterfit.modules.traininggroup.validation;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.traininggroup.entity.TrainingGroup;
import com.smarterfit.modules.traininggroup.repository.TrainingGroupRepository;

@Component
public class TrainingGroupValidation {
   public final TrainingGroupRepository trainingGroupRepository;

   public TrainingGroupValidation(TrainingGroupRepository trainingGroupRepository) {
      this.trainingGroupRepository = trainingGroupRepository;
   }

   public TrainingGroup validateTrainingGroupById(UUID id) {
      return trainingGroupRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Training group not found"));
   }

   public void validateFutureDateRange(LocalDate startDate, LocalDate endDate) {
      String messageError = "Start date must be in the future and end date must be in the future or null.";

      if (startDate != null && endDate != null) {
         if (startDate.isAfter(endDate) || endDate.isBefore(LocalDate.now())) {
            throw new BusinessException(messageError);
         }
      } else if (startDate != null && endDate == null) {
         if (startDate.isBefore(LocalDate.now())) {
            throw new BusinessException(messageError);
         }
      } else if (startDate == null && endDate != null) {
         if (endDate.isBefore(LocalDate.now())) {
            throw new BusinessException(messageError);
         }
      }
   }

   public void validateTrainingGroupDateRange(TrainingGroup trainingGroup) {
      if (trainingGroup.getStartDate() != null) {
         if (trainingGroup.getStartDate().isBefore(trainingGroup.getCreatedAt().toLocalDate())) {
            throw new BusinessException("Start date must be in the future.");
         }
      }

      validateFutureDateRange(trainingGroup.getStartDate(), trainingGroup.getEndDate());
   }

   public Boolean validateTrainingGroupStarted(TrainingGroup trainingGroup) {
      if (trainingGroup.getStartDate() == null || trainingGroup.getStartDate().isBefore(LocalDate.now())) {
         return true;
      }

      return false;
   }

   public Boolean validateTrainingGroupEnded(TrainingGroup trainingGroup) {
      if (trainingGroup.getEndDate() == null || trainingGroup.getEndDate().isAfter(LocalDate.now())) {
         return false;
      }

      return true;
   }

   public Boolean validateTrainingGroupIsActive(TrainingGroup trainingGroup) {
      if (trainingGroup.getStartDate() == null || trainingGroup.getStartDate().isBefore(LocalDate.now())) {
         if (trainingGroup.getEndDate() == null || trainingGroup.getEndDate().isAfter(LocalDate.now())) {
            return true;
         }
      }

      return false;
   }

   public void validateTrainingGroupActive(TrainingGroup trainingGroup) {
      if (!validateTrainingGroupIsActive(trainingGroup)) {
         throw new BusinessException("The training group is not active.");
      }
   }

   public void validateTrainingGroupNotActive(TrainingGroup trainingGroup) {
      if (validateTrainingGroupIsActive(trainingGroup)) {
         throw new BusinessException("The training group is active.");
      }
   }
}
