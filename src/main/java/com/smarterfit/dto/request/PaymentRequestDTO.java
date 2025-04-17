package com.smarterfit.dto.request;

import java.util.UUID;

import com.smarterfit.enums.PaymentMethod;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PaymentRequestDTO(
      @NotNull(message = "Subscription ID cannot be null") 
      UUID subscriptionId,

      @NotNull(message = "Amount cannot be null") 
      @Min(value = 0, message = "Amount must be greater than or equal to 0") 
      Double amount,

      @NotNull(message = "paymentMethod cannot be null") 
      PaymentMethod paymentMethod) {
}
