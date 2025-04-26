package com.smarterfit.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.enums.SubscriptionStatus;

public record SubscriptionResponseDTO(
      UUID id,
      UserResponseDTO owner,
      LocalDateTime startedIn,
      LocalDateTime renewedIn,
      LocalDateTime endedIn,
      SubscriptionStatus status,
      Integer availableMembers,
      Integer availableClasses) {
}
