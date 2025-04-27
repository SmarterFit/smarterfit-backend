package com.smarterfit.modules.billing.validation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.smarterfit.common.enums.SubscriptionStatus;
import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.billing.entity.Plan;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.repository.PlanRepository;

@Component
public class PlanValidation {

   private final PlanRepository planRepository;

   public PlanValidation(PlanRepository planRepository) {
      this.planRepository = planRepository;
   }

   public Plan validatePlanById(UUID id) {
      return planRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
   }

   public void validateNoActiveSubscriptions(Plan plan) {
      for (Subscription subscription : plan.getSubscriptions()) {
         if (subscription.getStatus() == SubscriptionStatus.ACTIVE) {
            throw new BusinessException("Exists active subscriptions for this plan.");
         }
      }
   }

   public void validatePlanNotDeleted(Plan plan) {
      if (plan.getDeletedAt() != null) {
         throw new BusinessException("Plan already deleted.");
      }
   }
}
