package com.smarterfit.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubscriptionShortResponseDTO(
      UUID id,
      PlanResponseDTO plan,
      LocalDateTime startedIn,
      LocalDateTime renewedIn,
      LocalDateTime endedIn,
      String status,
      Integer availableMembers) {

}
