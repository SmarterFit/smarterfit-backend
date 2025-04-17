package com.smarterfit.dto.response;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record SubscriptionResponseDTO(
      UUID id,
      UserShortResponseDTO owner,
      Set<UserShortResponseDTO> participants,
      PlanResponseDTO plan,
      LocalDateTime startedIn,
      LocalDateTime renewedIn,
      LocalDateTime endedIn,
      String status,
      Integer availableMembers,
      Set<PaymentShortResponseDTO> payments
      ) {
}
