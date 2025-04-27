package com.smarterfit.common.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smarterfit.modules.billing.service.PaymentService;

@Component
public class PaymentScheduler {
   private final PaymentService paymentService;

   public PaymentScheduler(PaymentService paymentService) {
      this.paymentService = paymentService;
   }

   @Scheduled(fixedRate = 1000 * 60) // Run every minute
   public void expirePayments() {
      paymentService.expirePaymentsIfNeeded();
   }
}
