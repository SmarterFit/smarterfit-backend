package com.smarterfit.modules.billing.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.billing.dto.request.payment.CreatePaymentRequestDTO;
import com.smarterfit.modules.billing.dto.response.payment.PaymentResponseDTO;
import com.smarterfit.modules.billing.dto.response.payment.PaymentWithSubscriptionResponseDTO;
import com.smarterfit.modules.billing.entity.Payment;
import com.smarterfit.modules.billing.entity.Subscription;

public class PaymentMapper {
   private PaymentMapper() {
      // Private constructor to prevent instantiation
   }

   public static Payment toEntity(CreatePaymentRequestDTO dto, Subscription subscription) {
      return toEntity(dto, subscription, new Payment());
   }

   public static Payment toEntity(CreatePaymentRequestDTO dto, Subscription subscription, Payment payment) {
      if (payment == null) {
         throw new ResourceNotFoundException("Payment not found.");
      }
      if (subscription == null) {
         throw new ResourceNotFoundException("Subscription not found.");
      }

      payment = GenericMapper.map(dto, payment);
      payment.setSubscription(subscription);
      payment.setAmount(subscription.getPlan().getPrice());

      return payment;
   }

   public static PaymentResponseDTO toResponse(Payment payment) {
      if (payment == null) {
         throw new ResourceNotFoundException("Payment not found.");
      }

      return GenericMapper.map(payment, PaymentResponseDTO.class);
   }

   public static PaymentWithSubscriptionResponseDTO toResponseWithSubscription(Payment payment) {
      if (payment == null) {
         throw new ResourceNotFoundException("Payment not found.");
      }

      PaymentWithSubscriptionResponseDTO response = GenericMapper.map(payment,
            PaymentWithSubscriptionResponseDTO.class);
      response = response.toBuilder().subscription(SubscriptionMapper.toResponse(payment.getSubscription())).build();

      return response;
   }
}
