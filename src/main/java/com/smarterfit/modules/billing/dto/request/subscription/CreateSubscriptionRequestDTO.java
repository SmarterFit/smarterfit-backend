package com.smarterfit.modules.billing.dto.request.subscription;

import java.util.UUID;

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
public class CreateSubscriptionRequestDTO {
      @NotNull(message = "Owner ID must not be null")
      private UUID ownerId;

      @NotNull(message = "Plan ID must not be null")
      private UUID planId;

      private Boolean addOwnerAsParticipant;
}
