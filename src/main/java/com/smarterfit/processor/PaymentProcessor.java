package com.smarterfit.processor;

import com.smarterfit.dto.request.PaymentProcessorRequestDTO;
import com.smarterfit.dto.response.PaymentProcessorResponseDTO;
import com.smarterfit.enums.PaymentMethod;

public interface PaymentProcessor {
   PaymentProcessorResponseDTO processPayment(PaymentProcessorRequestDTO paymentProcessorRequestDTO);

   PaymentMethod getPaymentMethod();
}
