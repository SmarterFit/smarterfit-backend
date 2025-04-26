package com.smarterfit.processor;

import org.springframework.stereotype.Component;

import com.smarterfit.dto.request.payment.ProcessorDTO;
import com.smarterfit.dto.response.payment.PaymentProcessorResponseDTO;
import com.smarterfit.enums.PaymentMethod;

@Component
public class CashPaymentProcessor implements PaymentProcessor {
   @Override
   public PaymentProcessorResponseDTO processPayment(ProcessorDTO processorDTO) {
      return new PaymentProcessorResponseDTO("Payment processed", true);
   }

   @Override
   public PaymentMethod getPaymentMethod() {
      return PaymentMethod.CASH;
   }
}
