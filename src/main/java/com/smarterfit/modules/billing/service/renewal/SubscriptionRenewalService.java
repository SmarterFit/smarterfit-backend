package com.smarterfit.modules.billing.service.renewal;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smarterfit.common.enums.SubscriptionStatus;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.repository.SubscriptionRepository;
import com.smarterfit.modules.billing.validation.SubscriptionValidation;

@Service
public class SubscriptionRenewalService {
   private final SubscriptionRepository subscriptionRepository;
   private final SubscriptionValidation subscriptionValidation;

   @Autowired
   public SubscriptionRenewalService(SubscriptionRepository subscriptionRepository,
         SubscriptionValidation subscriptionValidation) {
      this.subscriptionRepository = subscriptionRepository;
      this.subscriptionValidation = subscriptionValidation;
   }

   public void renewSubscription(Subscription subscription) {
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime endedIn = subscription.getEndedIn() != null ? subscription.getEndedIn() : now;
      Integer duration = subscription.getPlan().getDuration();
      LocalDateTime newEndDate = endedIn.isAfter(now) ? endedIn.plusDays(duration) : now.plusDays(duration);
      SubscriptionStatus status = subscription.getStatus();

      subscriptionValidation.validateSubscriptionNotIsCanceled(subscription);

      if (status == SubscriptionStatus.PENDING) {
         subscription.setStartedIn(now);
      }

      subscription.setStatus(SubscriptionStatus.ACTIVE);
      subscription.setRenewedIn(now);
      subscription.setEndedIn(newEndDate);

      subscriptionRepository.save(subscription);
   }
}
