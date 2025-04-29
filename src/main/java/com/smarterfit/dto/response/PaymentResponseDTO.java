package com.smarterfit.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.enums.PaymentMethod;
import com.smarterfit.enums.PaymentStatus;

public record PaymentResponseDTO(
      UUID id,
      SubscriptionShortResponseDTO subscription,
      Double amount,
      LocalDateTime paymentDate,
      PaymentMethod paymentMethod,
      PaymentStatus status) {
}
