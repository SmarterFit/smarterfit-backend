package com.smarterfit.modules.billing.dto.request.payment;

import java.util.List;
import java.util.UUID;

import com.smarterfit.common.enums.PaymentMethod;
import com.smarterfit.common.enums.PaymentStatus;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchPaymentRequestDTO(
            UUID subscriptionId,
            UUID subscriptionOwnerId,
            List<PaymentMethod> methods,
            List<PaymentStatus> status) {

}
