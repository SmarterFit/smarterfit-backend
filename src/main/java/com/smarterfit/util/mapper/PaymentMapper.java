package com.smarterfit.util.mapper;

import com.smarterfit.dto.response.PaymentResponseDTO;
import com.smarterfit.dto.response.PaymentShortResponseDTO;
import com.smarterfit.dto.response.SubscriptionShortResponseDTO;
import com.smarterfit.model.SubscriptionUser.Payment;

public class PaymentMapper {

   public static PaymentShortResponseDTO toShortResponse(Payment payment) {
      if (payment == null) {
         return null;
      }

      return new PaymentShortResponseDTO(
            payment.getId(),
            payment.getAmount(),
            payment.getPaymentDate(),
            payment.getPaymentMethod(),
            payment.getStatus());
   }

   public static PaymentResponseDTO toResponse(Payment payment) {
      if (payment == null) {
         return null;
      }

      SubscriptionShortResponseDTO subscription = SubscriptionMapper.toShortResponse(payment.getSubscription());

      return new PaymentResponseDTO(
            payment.getId(),
            subscription,
            payment.getAmount(),
            payment.getPaymentDate(),
            payment.getPaymentMethod(),
            payment.getStatus());
   }
}
