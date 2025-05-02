package com.smarterfit.modules.billing.event;

import org.springframework.context.ApplicationEvent;

import com.smarterfit.modules.billing.entity.Subscription;

import lombok.Getter;

@Getter
public class PaymentConfirmedEvent extends ApplicationEvent {
   private final Subscription subscription;

   public PaymentConfirmedEvent(Subscription subscription) {
      super(subscription);
      this.subscription = subscription;
   }
}
