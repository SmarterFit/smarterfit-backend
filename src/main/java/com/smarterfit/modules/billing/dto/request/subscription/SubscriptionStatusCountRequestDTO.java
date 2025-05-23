package com.smarterfit.modules.billing.dto.request.subscription;

import java.time.LocalDateTime;

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
public class SubscriptionStatusCountRequestDTO {
   @NotNull(message = "renewedFrom must not be null")
   private LocalDateTime renewedFrom;
   @NotNull(message = "renewedTo must not be null")
   private LocalDateTime renewedTo;

   @NotNull(message = "activeFrom must not be null")
   private LocalDateTime createdFrom;
   @NotNull(message = "activeTo must not be null")
   private LocalDateTime createdTo;

   @NotNull(message = "canceledFrom must not be null")
   private LocalDateTime canceledFrom;
   @NotNull(message = "canceledTo must not be null")
   private LocalDateTime canceledTo;

   @NotNull(message = "pendingFrom must not be null")
   private LocalDateTime pendingFrom;
   @NotNull(message = "pendingTo must not be null")
   private LocalDateTime pendingTo;

   @NotNull(message = "expiredFrom must not be null")
   private LocalDateTime expiredFrom;
   @NotNull(message = "expiredTo must not be null")
   private LocalDateTime expiredTo;
}
