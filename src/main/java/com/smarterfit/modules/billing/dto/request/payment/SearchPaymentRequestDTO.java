package com.smarterfit.modules.billing.dto.request.payment;

import java.util.List;
import java.util.UUID;

import com.smarterfit.common.enums.PaymentMethod;
import com.smarterfit.common.enums.PaymentStatus;

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
public class SearchPaymentRequestDTO {
    private UUID subscriptionId;
    private UUID subscriptionOwnerId;
    private List<PaymentMethod> methods;
    private List<PaymentStatus> status;
}
