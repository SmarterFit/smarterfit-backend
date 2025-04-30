package com.smarterfit.modules.billing.dto.response.payment;

import java.time.LocalDateTime;
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
public class PaymentResponseDTO {
   private UUID id;
   private Double amount;
   private LocalDateTime paymentDate;
   private LocalDateTime expirationIn;
   private PaymentMethod method;
   private PaymentStatus status;
}
