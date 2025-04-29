package com.smarterfit.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record SubscriptionUserRequestDTO(
   @NotNull(message = "User ID must not be null")
   UUID userId
) {

}
