package com.smarterfit.modules.billing.dto.response.subscription;

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
public class SubscriptionStatusCountResponseDTO {
   private Long renewedCount;
   private Long createdCount;
   private Long canceledCount;
   private Long pendingCount;
   private Long expiredCount;
   private Long activeCount;
}
