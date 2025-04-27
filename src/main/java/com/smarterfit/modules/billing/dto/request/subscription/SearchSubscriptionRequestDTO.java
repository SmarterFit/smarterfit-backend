package com.smarterfit.modules.billing.dto.request.subscription;

import java.util.List;
import java.util.UUID;

import com.smarterfit.common.enums.SubscriptionStatus;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchSubscriptionRequestDTO(
      UUID ownerId,
      UUID participantId,
      UUID planId,
      List<SubscriptionStatus> status) {

}
