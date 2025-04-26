package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.payment.PaymentDTO;
import com.smarterfit.dto.response.payment.PaymentResponseDTO;
import com.smarterfit.dto.response.payment.PaymentWithSubscriptionResponseDTO;
import com.smarterfit.enums.PaymentStatus;
import com.smarterfit.model.SubscriptionUser.Payment;
import com.smarterfit.model.SubscriptionUser.Subscription;

public class PaymentMapper {
   public static Payment toEntity(Subscription subscription, PaymentDTO dto) {
      Payment payment = new Payment();
      payment.setSubscription(subscription);
      payment.setAmount(subscription.getPlan().getPrice());
      payment.setPaymentMethod(dto.paymentMethod());
      payment.setStatus(PaymentStatus.PENDING);

      return payment;
   }

   public static PaymentResponseDTO toResponse(Payment payment) {
      if (payment == null) {
         return null;
      }

      return new PaymentResponseDTO(
            payment.getId(),
            payment.getAmount(),
            payment.getPaymentDate(),
            payment.getPaymentMethod(),
            payment.getStatus());
   }

   public static PaymentWithSubscriptionResponseDTO toResponseWithSubscription(Payment payment) {
      if (payment == null) {
         return null;
      }

      return new PaymentWithSubscriptionResponseDTO(
            payment.getId(),
            SubscriptionMapper.toResponse(payment.getSubscription()),
            payment.getAmount(),
            payment.getPaymentDate(),
            payment.getPaymentMethod(),
            payment.getStatus());
   }
}
