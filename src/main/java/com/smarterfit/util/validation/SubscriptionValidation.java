package com.smarterfit.util.validation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.SubscriptionUser.Subscription;
import com.smarterfit.repository.SubscriptionRepository;

@Component
public class SubscriptionValidation {
   private final SubscriptionRepository subscriptionRepository;

   public SubscriptionValidation(SubscriptionRepository subscriptionRepository) {
      this.subscriptionRepository = subscriptionRepository;
   }

   public Subscription findSubscriptionById(UUID id) {
      return subscriptionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
   }
}
