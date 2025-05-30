package com.smarterfit.modules.traininggroup.validation;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.util.SlugUtils;
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

   public TrainingGroup validateTrainingGroupBySlug(String slug) {
      return trainingGroupRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException("Training group not found"));
   }

   public void validateFutureDateRange(LocalDateTime startDate, LocalDateTime endDate) {
      String messageError = "Start date must be in the future and end date must be in the future or null.";

      if (startDate != null && endDate != null) {
         if (startDate.isAfter(endDate) || endDate.isBefore(LocalDateTime.now())) {
            throw new BusinessException(messageError);
         }
      } else if (startDate != null && endDate == null) {
         if (startDate.isBefore(LocalDateTime.now())) {
            throw new BusinessException(messageError);
         }
      } else if (startDate == null && endDate != null) {
         if (endDate.isBefore(LocalDateTime.now())) {
            throw new BusinessException(messageError);
         }
      }
   }

   public void validateTrainingGroupDateRange(TrainingGroup trainingGroup) {
      LocalDateTime startDate = trainingGroup.getStartDate();
      LocalDateTime createdAt = trainingGroup.getCreatedAt();

      if (startDate!= null) {
         if (startDate.isBefore(createdAt)) {
            throw new BusinessException("Start date must be in the future.");
         }
      }

      validateFutureDateRange(trainingGroup.getStartDate(), trainingGroup.getEndDate());
   }

   public Boolean validateTrainingGroupStarted(TrainingGroup trainingGroup) {
      if (trainingGroup.getStartDate() == null || trainingGroup.getStartDate().isBefore(LocalDateTime.now())) {
         return true;
      }

      return false;
   }

   public Boolean validateTrainingGroupEnded(TrainingGroup trainingGroup) {
      if (trainingGroup.getEndDate() == null || trainingGroup.getEndDate().isAfter(LocalDateTime.now())) {
         return false;
      }

      return true;
   }

   public Boolean validateTrainingGroupIsActive(TrainingGroup trainingGroup) {
      LocalDateTime today = LocalDateTime.now();
      LocalDateTime startDate = trainingGroup.getStartDate();
      LocalDateTime endDate = trainingGroup.getEndDate();

      if (startDate == null || startDate.isEqual(today) || startDate.isBefore(today)) {
         if (endDate == null || endDate.isEqual(today) || endDate.isAfter(today)) {
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

   public String generateUniqueSlug(String name) {
      String baseSlug = SlugUtils.slugify(name);
      String slug = baseSlug;
      int counter = 1;

      while (trainingGroupRepository.existsBySlug(slug)) {
         slug = baseSlug + "-" + counter++;
      }

      return slug;
   }
}
