package com.smarterfit.dto.request;

import java.util.UUID;

import com.smarterfit.enums.PaymentMethod;

import jakarta.validation.constraints.NotNull;

public record PaymentRequestDTO(
      @NotNull(message = "Subscription ID cannot be null") 
      UUID subscriptionId,

      @NotNull(message = "paymentMethod cannot be null") 
      PaymentMethod paymentMethod) {
}
