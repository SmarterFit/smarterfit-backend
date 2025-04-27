package com.smarterfit.common.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smarterfit.modules.billing.service.SubscriptionService;

@Component
public class SubscriptionScheduler {
   private final SubscriptionService subscriptionService;

   public SubscriptionScheduler(SubscriptionService subscriptionService) {
      this.subscriptionService = subscriptionService;
   }

   @Scheduled(cron = "0 0 3 * * *") // Runs every day at 3 AM
   public void expireSubscriptions() {
      subscriptionService.expireSubscriptionsIfNeeded();
   }
}
