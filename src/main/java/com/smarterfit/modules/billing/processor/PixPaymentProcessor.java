package com.smarterfit.modules.billing.processor;

import org.springframework.stereotype.Component;

import com.smarterfit.common.enums.PaymentMethod;
import com.smarterfit.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.smarterfit.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;

@Component
public class PixPaymentProcessor implements PaymentProcessor {
   @Override
   public PaymentProcessorResponseDTO processPayment(ProcessorPaymentRequestDTO processorDTO) {
      return new PaymentProcessorResponseDTO("Payment processed", true);
   }

   @Override
   public PaymentMethod getPaymentMethod() {
      return PaymentMethod.PIX;
   }
}
