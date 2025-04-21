package com.smarterfit.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.enums.PaymentMethod;
import com.smarterfit.enums.PaymentStatus;

public record PaymentShortResponseDTO(
      UUID id,
      Double amount,
      LocalDateTime paymentDate,
      PaymentMethod paymentMethod,
      PaymentStatus status) {
}
