package com.smarterfit.dto.response.payment;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.enums.PaymentMethod;
import com.smarterfit.enums.PaymentStatus;

public record PaymentResponseDTO(
      UUID id,
      Double amount,
      LocalDateTime paymentDate,
      PaymentMethod paymentMethod,
      PaymentStatus status) {
}
