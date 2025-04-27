package com.smarterfit.modules.billing.dto.response.payment;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.common.enums.PaymentMethod;
import com.smarterfit.common.enums.PaymentStatus;
import lombok.Builder;

@Builder(toBuilder = true)
public record PaymentResponseDTO(
            UUID id,
            Double amount,
            LocalDateTime paymentDate,
            LocalDateTime expirationIn,
            PaymentMethod method,
            PaymentStatus status) {
}
