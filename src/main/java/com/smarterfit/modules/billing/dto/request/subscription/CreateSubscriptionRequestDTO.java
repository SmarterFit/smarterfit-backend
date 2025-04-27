package com.smarterfit.modules.billing.dto.request.subscription;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(toBuilder = true)
public record CreateSubscriptionRequestDTO(
      @NotNull(message = "Owner ID must not be null") UUID ownerId,
      @NotNull(message = "Plan ID must not be null") UUID planId,
      Boolean addOwnerAsParticipant
      ) {
}
