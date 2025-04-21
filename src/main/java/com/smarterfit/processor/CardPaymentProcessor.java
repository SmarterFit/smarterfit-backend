package com.smarterfit.processor;

import org.springframework.stereotype.Component;

import com.smarterfit.dto.request.PaymentProcessorRequestDTO;
import com.smarterfit.dto.response.PaymentProcessorResponseDTO;
import com.smarterfit.enums.PaymentMethod;

@Component
public class CardPaymentProcessor implements PaymentProcessor {
   @Override
   public PaymentProcessorResponseDTO processPayment(PaymentProcessorRequestDTO paymentProcessorRequestDTO) {
      return new PaymentProcessorResponseDTO("Payment processed", true);
   }

   @Override
   public PaymentMethod getPaymentMethod() {
      return PaymentMethod.CARD;
   }
}
