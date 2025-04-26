package com.smarterfit.dto.request.payment;

import java.util.List;
import java.util.UUID;

import com.smarterfit.enums.PaymentMethod;
import com.smarterfit.enums.PaymentStatus;

public record SearchDTO(
            UUID subscriptionId,
            UUID subscriptionOwnerId,
            List<PaymentMethod> paymentMethods,
            List<PaymentStatus> status) {

}
