package com.smarterfit.modules.billing.validation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.smarterfit.common.enums.SubscriptionStatus;
import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.repository.SubscriptionRepository;

@Component
public class SubscriptionValidation {
   private final SubscriptionRepository subscriptionRepository;

   public SubscriptionValidation(SubscriptionRepository subscriptionRepository) {
      this.subscriptionRepository = subscriptionRepository;
   }

   public Subscription validateSubscriptionById(UUID id) {
      return subscriptionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
   }

   public void validateSubscriptionNotIsCanceled(Subscription subscription) {
      if (subscription.getStatus() == SubscriptionStatus.CANCELED) {
         throw new BusinessException("Subscription is canceled.");
      }
   }
}
