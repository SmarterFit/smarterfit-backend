package com.smarterfit.modules.billing.dto.response.payment;

import lombok.Builder;

@Builder(toBuilder = true)
public record PaymentProcessorResponseDTO(
            String message,
            Boolean success) {
}
