package com.smarterfit.modules.billing.dto.request.payment;

import java.util.UUID;

import com.smarterfit.common.enums.PaymentMethod;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
public record CreatePaymentRequestDTO(
      @NotNull(message = "Subscription ID cannot be null") 
      UUID subscriptionId,
      
      @NotNull(message = "method cannot be null") 
      PaymentMethod method) {
}
