package com.smarterfit.dto.request.subscription;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record SubscriptionDTO(
      @NotNull(message = "Owner ID must not be null") UUID ownerId,
      @NotNull(message = "Plan ID must not be null") UUID planId,
      Boolean addOwnerAsParticipant
      ) {
}
