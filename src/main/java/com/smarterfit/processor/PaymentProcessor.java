package com.smarterfit.processor;

import com.smarterfit.dto.request.payment.ProcessorDTO;
import com.smarterfit.dto.response.payment.PaymentProcessorResponseDTO;
import com.smarterfit.enums.PaymentMethod;

public interface PaymentProcessor {
   PaymentProcessorResponseDTO processPayment(ProcessorDTO processorDTO);

   PaymentMethod getPaymentMethod();
}
