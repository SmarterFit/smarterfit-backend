package com.smarterfit.scheduler;

import com.smarterfit.service.ClassSessionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smarterfit.service.SubscriptionService;

@Component
public class SubscriptionScheduler {
   private final SubscriptionService subscriptionService;
   private final ClassSessionService classSessionService;

   public SubscriptionScheduler(SubscriptionService subscriptionService,
                                ClassSessionService classSessionService) {
      this.subscriptionService = subscriptionService;
        this.classSessionService = classSessionService;
   }

   @Scheduled(cron = "0 0 3 * * *")
   public void processSubscriptions() {
      subscriptionService.expireSubscriptionsIfNeeded();
   }


   @Scheduled(cron = "0 0 2 * * 0") // Every Sunday at 2 AM
   public void processClassSessions() {classSessionService.generateDailySessions();}
}
