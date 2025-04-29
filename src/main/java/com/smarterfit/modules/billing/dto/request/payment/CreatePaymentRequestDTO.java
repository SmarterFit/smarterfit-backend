package com.smarterfit.modules.billing.dto.request.payment;

import java.util.UUID;

import com.smarterfit.common.enums.PaymentMethod;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class CreatePaymentRequestDTO {

    @NotNull(message = "Subscription ID cannot be null")
    private UUID subscriptionId;

    @NotNull(message = "method cannot be null")
    private PaymentMethod method;
}
