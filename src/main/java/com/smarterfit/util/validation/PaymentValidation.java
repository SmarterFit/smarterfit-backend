package com.smarterfit.util.validation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.SubscriptionUser.Payment;
import com.smarterfit.repository.PaymentRepository;

@Component
public class PaymentValidation {
   private final PaymentRepository paymentRepository;

   public PaymentValidation(PaymentRepository paymentRepository) {
      this.paymentRepository = paymentRepository;
   }

   public Payment findPaymentById(UUID id) {
      return paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
   }
}
