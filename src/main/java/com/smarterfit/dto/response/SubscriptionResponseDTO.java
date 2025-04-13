package com.smarterfit.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubscriptionResponseDTO(
   UUID id,
   UUID ownerId,
   UUID planId,
   LocalDateTime startedIn,
   LocalDateTime renewedIn,
   LocalDateTime endedIn,
   /// Subscription Users
   String status) {

   }
