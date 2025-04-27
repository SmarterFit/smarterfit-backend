package com.smarterfit.modules.billing.processor;

import com.smarterfit.common.enums.PaymentMethod;
import com.smarterfit.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.smarterfit.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;

public interface PaymentProcessor {
   PaymentProcessorResponseDTO processPayment(ProcessorPaymentRequestDTO processorDTO);

   PaymentMethod getPaymentMethod();
}
