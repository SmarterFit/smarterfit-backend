package com.smarterfit.modules.checkin.validation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.modules.checkin.entity.GymCheckIn;
import com.smarterfit.modules.checkin.repository.GymCheckInRepository;

@Component
public class GymCheckInValidation {
   private final GymCheckInRepository gymCheckInRepository;

   public GymCheckInValidation(GymCheckInRepository gymCheckInRepository) {
      this.gymCheckInRepository = gymCheckInRepository;
   }

   public GymCheckIn validateOpenGymCheckInByUserId(UUID userId) {
      return gymCheckInRepository.findFirstByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(userId)
            .orElseThrow(() -> new BusinessException("Gym check-in not found"));
   }

   public void validateOpenCheckInNotExists(UUID userId) {
      if (gymCheckInRepository.findFirstByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(userId).isPresent()) {
         throw new BusinessException("User already has a check-in");
      }
   }
}
