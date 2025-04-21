package com.smarterfit.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smarterfit.service.SubscriptionService;

@Component
public class SubscriptionScheduler {
   private final SubscriptionService subscriptionService;

   public SubscriptionScheduler(SubscriptionService subscriptionService) {
      this.subscriptionService = subscriptionService;
   }

   @Scheduled(cron = "0 0 3 * * *")
   public void processSubscriptions() {
      subscriptionService.expireSubscriptionsIfNeeded();
   }
}
