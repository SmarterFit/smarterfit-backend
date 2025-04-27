package com.smarterfit.modules.billing.validation;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.smarterfit.common.enums.PaymentStatus;
import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.billing.entity.Payment;
import com.smarterfit.modules.billing.repository.PaymentRepository;

@Component
public class PaymentValidation {
   private final PaymentRepository paymentRepository;

   public PaymentValidation(PaymentRepository paymentRepository) {
      this.paymentRepository = paymentRepository;
   }

   public Payment validatePaymentById(UUID id) {
      return paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
   }

   public void validatePaymentIsPending(Payment payment) {
      if (payment.getStatus() != PaymentStatus.PENDING) {
         throw new BusinessException("Payment does not have a pending status");
      }
   }

   public void validatePaymentNotExpired(Payment payment) {
      if (payment.getExpirationIn().isBefore(LocalDateTime.now())) {
         throw new BusinessException("Payment has expired");
      }
   }
}
