package com.smarterfit.dto.request.subscription;

import java.util.List;
import java.util.UUID;


import com.smarterfit.enums.SubscriptionStatus;

public record SearchDTO(
      UUID ownerId,
      UUID planId,
      List<SubscriptionStatus> status) {

}
