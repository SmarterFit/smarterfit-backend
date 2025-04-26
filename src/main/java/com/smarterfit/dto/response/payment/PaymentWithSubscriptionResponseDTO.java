package com.smarterfit.dto.response.payment;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.dto.response.SubscriptionResponseDTO;
import com.smarterfit.enums.PaymentMethod;
import com.smarterfit.enums.PaymentStatus;

public record PaymentWithSubscriptionResponseDTO(
            UUID id,
            SubscriptionResponseDTO subscription,
            Double amount,
            LocalDateTime paymentDate,
            PaymentMethod paymentMethod,
            PaymentStatus status) {

}
