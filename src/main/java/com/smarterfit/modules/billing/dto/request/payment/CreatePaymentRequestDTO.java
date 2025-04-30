package com.smarterfit.modules.billing.dto.request.payment;

import java.util.UUID;

import com.smarterfit.common.enums.PaymentMethod;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreatePaymentRequestDTO {
    @NotNull(message = "Subscription ID cannot be null")
    private UUID subscriptionId;

    @NotNull(message = "method cannot be null")
    private PaymentMethod method;
}
